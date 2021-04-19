package com.google.protobuf;

import java.nio.*;
import java.nio.charset.*;
import java.io.*;
import java.util.*;

final class RopeByteString extends ByteString
{
    static final int[] minLengthByDepth;
    private final int totalLength;
    private final ByteString left;
    private final ByteString right;
    private final int leftLength;
    private final int treeDepth;
    private static final long serialVersionUID = 1L;
    
    private RopeByteString(final ByteString left, final ByteString right) {
        this.left = left;
        this.right = right;
        this.leftLength = left.size();
        this.totalLength = this.leftLength + right.size();
        this.treeDepth = Math.max(left.getTreeDepth(), right.getTreeDepth()) + 1;
    }
    
    static ByteString concatenate(final ByteString left, final ByteString right) {
        if (right.size() == 0) {
            return left;
        }
        if (left.size() == 0) {
            return right;
        }
        final int newLength = left.size() + right.size();
        if (newLength < 128) {
            return concatenateBytes(left, right);
        }
        if (left instanceof RopeByteString) {
            final RopeByteString leftRope = (RopeByteString)left;
            if (leftRope.right.size() + right.size() < 128) {
                final ByteString newRight = concatenateBytes(leftRope.right, right);
                return new RopeByteString(leftRope.left, newRight);
            }
            if (leftRope.left.getTreeDepth() > leftRope.right.getTreeDepth() && leftRope.getTreeDepth() > right.getTreeDepth()) {
                final ByteString newRight = new RopeByteString(leftRope.right, right);
                return new RopeByteString(leftRope.left, newRight);
            }
        }
        final int newDepth = Math.max(left.getTreeDepth(), right.getTreeDepth()) + 1;
        if (newLength >= minLength(newDepth)) {
            return new RopeByteString(left, right);
        }
        return new Balancer().balance(left, right);
    }
    
    private static ByteString concatenateBytes(final ByteString left, final ByteString right) {
        final int leftSize = left.size();
        final int rightSize = right.size();
        final byte[] bytes = new byte[leftSize + rightSize];
        left.copyTo(bytes, 0, 0, leftSize);
        right.copyTo(bytes, 0, leftSize, rightSize);
        return ByteString.wrap(bytes);
    }
    
    static RopeByteString newInstanceForTest(final ByteString left, final ByteString right) {
        return new RopeByteString(left, right);
    }
    
    static int minLength(final int depth) {
        if (depth >= RopeByteString.minLengthByDepth.length) {
            return Integer.MAX_VALUE;
        }
        return RopeByteString.minLengthByDepth[depth];
    }
    
    @Override
    public byte byteAt(final int index) {
        ByteString.checkIndex(index, this.totalLength);
        return this.internalByteAt(index);
    }
    
    @Override
    byte internalByteAt(final int index) {
        if (index < this.leftLength) {
            return this.left.internalByteAt(index);
        }
        return this.right.internalByteAt(index - this.leftLength);
    }
    
    @Override
    public int size() {
        return this.totalLength;
    }
    
    @Override
    public ByteIterator iterator() {
        return new AbstractByteIterator() {
            final PieceIterator pieces = new PieceIterator((ByteString)RopeByteString.this);
            ByteIterator current = this.nextPiece();
            
            private ByteIterator nextPiece() {
                return this.pieces.hasNext() ? this.pieces.next().iterator() : null;
            }
            
            @Override
            public boolean hasNext() {
                return this.current != null;
            }
            
            @Override
            public byte nextByte() {
                if (this.current == null) {
                    throw new NoSuchElementException();
                }
                final byte b = this.current.nextByte();
                if (!this.current.hasNext()) {
                    this.current = this.nextPiece();
                }
                return b;
            }
        };
    }
    
    @Override
    protected int getTreeDepth() {
        return this.treeDepth;
    }
    
    @Override
    protected boolean isBalanced() {
        return this.totalLength >= minLength(this.treeDepth);
    }
    
    @Override
    public ByteString substring(final int beginIndex, final int endIndex) {
        final int length = ByteString.checkRange(beginIndex, endIndex, this.totalLength);
        if (length == 0) {
            return ByteString.EMPTY;
        }
        if (length == this.totalLength) {
            return this;
        }
        if (endIndex <= this.leftLength) {
            return this.left.substring(beginIndex, endIndex);
        }
        if (beginIndex >= this.leftLength) {
            return this.right.substring(beginIndex - this.leftLength, endIndex - this.leftLength);
        }
        final ByteString leftSub = this.left.substring(beginIndex);
        final ByteString rightSub = this.right.substring(0, endIndex - this.leftLength);
        return new RopeByteString(leftSub, rightSub);
    }
    
    @Override
    protected void copyToInternal(final byte[] target, final int sourceOffset, final int targetOffset, final int numberToCopy) {
        if (sourceOffset + numberToCopy <= this.leftLength) {
            this.left.copyToInternal(target, sourceOffset, targetOffset, numberToCopy);
        }
        else if (sourceOffset >= this.leftLength) {
            this.right.copyToInternal(target, sourceOffset - this.leftLength, targetOffset, numberToCopy);
        }
        else {
            final int leftLength = this.leftLength - sourceOffset;
            this.left.copyToInternal(target, sourceOffset, targetOffset, leftLength);
            this.right.copyToInternal(target, 0, targetOffset + leftLength, numberToCopy - leftLength);
        }
    }
    
    @Override
    public void copyTo(final ByteBuffer target) {
        this.left.copyTo(target);
        this.right.copyTo(target);
    }
    
    @Override
    public ByteBuffer asReadOnlyByteBuffer() {
        final ByteBuffer byteBuffer = ByteBuffer.wrap(this.toByteArray());
        return byteBuffer.asReadOnlyBuffer();
    }
    
    @Override
    public List<ByteBuffer> asReadOnlyByteBufferList() {
        final List<ByteBuffer> result = new ArrayList<ByteBuffer>();
        final PieceIterator pieces = new PieceIterator((ByteString)this);
        while (pieces.hasNext()) {
            final LeafByteString byteString = pieces.next();
            result.add(byteString.asReadOnlyByteBuffer());
        }
        return result;
    }
    
    @Override
    public void writeTo(final OutputStream outputStream) throws IOException {
        this.left.writeTo(outputStream);
        this.right.writeTo(outputStream);
    }
    
    @Override
    void writeToInternal(final OutputStream out, final int sourceOffset, final int numberToWrite) throws IOException {
        if (sourceOffset + numberToWrite <= this.leftLength) {
            this.left.writeToInternal(out, sourceOffset, numberToWrite);
        }
        else if (sourceOffset >= this.leftLength) {
            this.right.writeToInternal(out, sourceOffset - this.leftLength, numberToWrite);
        }
        else {
            final int numberToWriteInLeft = this.leftLength - sourceOffset;
            this.left.writeToInternal(out, sourceOffset, numberToWriteInLeft);
            this.right.writeToInternal(out, 0, numberToWrite - numberToWriteInLeft);
        }
    }
    
    @Override
    void writeTo(final ByteOutput output) throws IOException {
        this.left.writeTo(output);
        this.right.writeTo(output);
    }
    
    @Override
    void writeToReverse(final ByteOutput output) throws IOException {
        this.right.writeToReverse(output);
        this.left.writeToReverse(output);
    }
    
    @Override
    protected String toStringInternal(final Charset charset) {
        return new String(this.toByteArray(), charset);
    }
    
    @Override
    public boolean isValidUtf8() {
        final int leftPartial = this.left.partialIsValidUtf8(0, 0, this.leftLength);
        final int state = this.right.partialIsValidUtf8(leftPartial, 0, this.right.size());
        return state == 0;
    }
    
    @Override
    protected int partialIsValidUtf8(final int state, final int offset, final int length) {
        final int toIndex = offset + length;
        if (toIndex <= this.leftLength) {
            return this.left.partialIsValidUtf8(state, offset, length);
        }
        if (offset >= this.leftLength) {
            return this.right.partialIsValidUtf8(state, offset - this.leftLength, length);
        }
        final int leftLength = this.leftLength - offset;
        final int leftPartial = this.left.partialIsValidUtf8(state, offset, leftLength);
        return this.right.partialIsValidUtf8(leftPartial, 0, length - leftLength);
    }
    
    @Override
    public boolean equals(final Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ByteString)) {
            return false;
        }
        final ByteString otherByteString = (ByteString)other;
        if (this.totalLength != otherByteString.size()) {
            return false;
        }
        if (this.totalLength == 0) {
            return true;
        }
        final int thisHash = this.peekCachedHashCode();
        final int thatHash = otherByteString.peekCachedHashCode();
        return (thisHash == 0 || thatHash == 0 || thisHash == thatHash) && this.equalsFragments(otherByteString);
    }
    
    private boolean equalsFragments(final ByteString other) {
        int thisOffset = 0;
        final Iterator<LeafByteString> thisIter = new PieceIterator((ByteString)this);
        LeafByteString thisString = thisIter.next();
        int thatOffset = 0;
        final Iterator<LeafByteString> thatIter = new PieceIterator(other);
        LeafByteString thatString = thatIter.next();
        int pos = 0;
        while (true) {
            final int thisRemaining = thisString.size() - thisOffset;
            final int thatRemaining = thatString.size() - thatOffset;
            final int bytesToCompare = Math.min(thisRemaining, thatRemaining);
            final boolean stillEqual = (thisOffset == 0) ? thisString.equalsRange(thatString, thatOffset, bytesToCompare) : thatString.equalsRange(thisString, thisOffset, bytesToCompare);
            if (!stillEqual) {
                return false;
            }
            pos += bytesToCompare;
            if (pos >= this.totalLength) {
                if (pos == this.totalLength) {
                    return true;
                }
                throw new IllegalStateException();
            }
            else {
                if (bytesToCompare == thisRemaining) {
                    thisOffset = 0;
                    thisString = thisIter.next();
                }
                else {
                    thisOffset += bytesToCompare;
                }
                if (bytesToCompare == thatRemaining) {
                    thatOffset = 0;
                    thatString = thatIter.next();
                }
                else {
                    thatOffset += bytesToCompare;
                }
            }
        }
    }
    
    @Override
    protected int partialHash(final int h, final int offset, final int length) {
        final int toIndex = offset + length;
        if (toIndex <= this.leftLength) {
            return this.left.partialHash(h, offset, length);
        }
        if (offset >= this.leftLength) {
            return this.right.partialHash(h, offset - this.leftLength, length);
        }
        final int leftLength = this.leftLength - offset;
        final int leftPartial = this.left.partialHash(h, offset, leftLength);
        return this.right.partialHash(leftPartial, 0, length - leftLength);
    }
    
    @Override
    public CodedInputStream newCodedInput() {
        return CodedInputStream.newInstance(new RopeInputStream());
    }
    
    @Override
    public InputStream newInput() {
        return new RopeInputStream();
    }
    
    Object writeReplace() {
        return ByteString.wrap(this.toByteArray());
    }
    
    private void readObject(final ObjectInputStream in) throws IOException {
        throw new InvalidObjectException("RopeByteStream instances are not to be serialized directly");
    }
    
    static {
        minLengthByDepth = new int[] { 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, 17711, 28657, 46368, 75025, 121393, 196418, 317811, 514229, 832040, 1346269, 2178309, 3524578, 5702887, 9227465, 14930352, 24157817, 39088169, 63245986, 102334155, 165580141, 267914296, 433494437, 701408733, 1134903170, 1836311903, Integer.MAX_VALUE };
    }
    
    private static class Balancer
    {
        private final ArrayDeque<ByteString> prefixesStack;
        
        private Balancer() {
            this.prefixesStack = new ArrayDeque<ByteString>();
        }
        
        private ByteString balance(final ByteString left, final ByteString right) {
            this.doBalance(left);
            this.doBalance(right);
            ByteString partialString = this.prefixesStack.pop();
            while (!this.prefixesStack.isEmpty()) {
                final ByteString newLeft = this.prefixesStack.pop();
                partialString = new RopeByteString(newLeft, partialString, null);
            }
            return partialString;
        }
        
        private void doBalance(final ByteString root) {
            if (root.isBalanced()) {
                this.insert(root);
            }
            else {
                if (!(root instanceof RopeByteString)) {
                    throw new IllegalArgumentException("Has a new type of ByteString been created? Found " + root.getClass());
                }
                final RopeByteString rbs = (RopeByteString)root;
                this.doBalance(rbs.left);
                this.doBalance(rbs.right);
            }
        }
        
        private void insert(final ByteString byteString) {
            int depthBin = this.getDepthBinForLength(byteString.size());
            int binEnd = RopeByteString.minLength(depthBin + 1);
            if (this.prefixesStack.isEmpty() || this.prefixesStack.peek().size() >= binEnd) {
                this.prefixesStack.push(byteString);
            }
            else {
                final int binStart = RopeByteString.minLength(depthBin);
                ByteString newTree = this.prefixesStack.pop();
                while (!this.prefixesStack.isEmpty() && this.prefixesStack.peek().size() < binStart) {
                    final ByteString left = this.prefixesStack.pop();
                    newTree = new RopeByteString(left, newTree, null);
                }
                newTree = new RopeByteString(newTree, byteString, null);
                while (!this.prefixesStack.isEmpty()) {
                    depthBin = this.getDepthBinForLength(newTree.size());
                    binEnd = RopeByteString.minLength(depthBin + 1);
                    if (this.prefixesStack.peek().size() >= binEnd) {
                        break;
                    }
                    final ByteString left = this.prefixesStack.pop();
                    newTree = new RopeByteString(left, newTree, null);
                }
                this.prefixesStack.push(newTree);
            }
        }
        
        private int getDepthBinForLength(final int length) {
            int depth = Arrays.binarySearch(RopeByteString.minLengthByDepth, length);
            if (depth < 0) {
                final int insertionPoint = -(depth + 1);
                depth = insertionPoint - 1;
            }
            return depth;
        }
    }
    
    private static final class PieceIterator implements Iterator<LeafByteString>
    {
        private final ArrayDeque<RopeByteString> breadCrumbs;
        private LeafByteString next;
        
        private PieceIterator(final ByteString root) {
            if (root instanceof RopeByteString) {
                final RopeByteString rbs = (RopeByteString)root;
                (this.breadCrumbs = new ArrayDeque<RopeByteString>(rbs.getTreeDepth())).push(rbs);
                this.next = this.getLeafByLeft(rbs.left);
            }
            else {
                this.breadCrumbs = null;
                this.next = (LeafByteString)root;
            }
        }
        
        private LeafByteString getLeafByLeft(final ByteString root) {
            ByteString pos;
            RopeByteString rbs;
            for (pos = root; pos instanceof RopeByteString; pos = rbs.left) {
                rbs = (RopeByteString)pos;
                this.breadCrumbs.push(rbs);
            }
            return (LeafByteString)pos;
        }
        
        private LeafByteString getNextNonEmptyLeaf() {
            while (this.breadCrumbs != null && !this.breadCrumbs.isEmpty()) {
                final LeafByteString result = this.getLeafByLeft(this.breadCrumbs.pop().right);
                if (!result.isEmpty()) {
                    return result;
                }
            }
            return null;
        }
        
        @Override
        public boolean hasNext() {
            return this.next != null;
        }
        
        @Override
        public LeafByteString next() {
            if (this.next == null) {
                throw new NoSuchElementException();
            }
            final LeafByteString result = this.next;
            this.next = this.getNextNonEmptyLeaf();
            return result;
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    private class RopeInputStream extends InputStream
    {
        private PieceIterator pieceIterator;
        private LeafByteString currentPiece;
        private int currentPieceSize;
        private int currentPieceIndex;
        private int currentPieceOffsetInRope;
        private int mark;
        
        public RopeInputStream() {
            this.initialize();
        }
        
        @Override
        public int read(final byte[] b, final int offset, final int length) {
            if (b == null) {
                throw new NullPointerException();
            }
            if (offset < 0 || length < 0 || length > b.length - offset) {
                throw new IndexOutOfBoundsException();
            }
            final int bytesRead = this.readSkipInternal(b, offset, length);
            if (bytesRead == 0) {
                return -1;
            }
            return bytesRead;
        }
        
        @Override
        public long skip(long length) {
            if (length < 0L) {
                throw new IndexOutOfBoundsException();
            }
            if (length > 2147483647L) {
                length = 2147483647L;
            }
            return this.readSkipInternal(null, 0, (int)length);
        }
        
        private int readSkipInternal(final byte[] b, int offset, final int length) {
            int bytesRemaining;
            int count;
            for (bytesRemaining = length; bytesRemaining > 0; bytesRemaining -= count) {
                this.advanceIfCurrentPieceFullyRead();
                if (this.currentPiece == null) {
                    break;
                }
                final int currentPieceRemaining = this.currentPieceSize - this.currentPieceIndex;
                count = Math.min(currentPieceRemaining, bytesRemaining);
                if (b != null) {
                    this.currentPiece.copyTo(b, this.currentPieceIndex, offset, count);
                    offset += count;
                }
                this.currentPieceIndex += count;
            }
            return length - bytesRemaining;
        }
        
        @Override
        public int read() throws IOException {
            this.advanceIfCurrentPieceFullyRead();
            if (this.currentPiece == null) {
                return -1;
            }
            return this.currentPiece.byteAt(this.currentPieceIndex++) & 0xFF;
        }
        
        @Override
        public int available() throws IOException {
            final int bytesRead = this.currentPieceOffsetInRope + this.currentPieceIndex;
            return RopeByteString.this.size() - bytesRead;
        }
        
        @Override
        public boolean markSupported() {
            return true;
        }
        
        @Override
        public void mark(final int readAheadLimit) {
            this.mark = this.currentPieceOffsetInRope + this.currentPieceIndex;
        }
        
        @Override
        public synchronized void reset() {
            this.initialize();
            this.readSkipInternal(null, 0, this.mark);
        }
        
        private void initialize() {
            this.pieceIterator = new PieceIterator((ByteString)RopeByteString.this);
            this.currentPiece = this.pieceIterator.next();
            this.currentPieceSize = this.currentPiece.size();
            this.currentPieceIndex = 0;
            this.currentPieceOffsetInRope = 0;
        }
        
        private void advanceIfCurrentPieceFullyRead() {
            if (this.currentPiece != null && this.currentPieceIndex == this.currentPieceSize) {
                this.currentPieceOffsetInRope += this.currentPieceSize;
                this.currentPieceIndex = 0;
                if (this.pieceIterator.hasNext()) {
                    this.currentPiece = this.pieceIterator.next();
                    this.currentPieceSize = this.currentPiece.size();
                }
                else {
                    this.currentPiece = null;
                    this.currentPieceSize = 0;
                }
            }
        }
    }
}
