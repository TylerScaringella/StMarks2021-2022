package me.tyler.encoding;

public class Branch<E> {

    private boolean leaf;
    private E info;
    private Branch left, right;

    public Branch(E info) {
        this.info = info;

        this.leaf = true;
    }

    public Branch(Branch left, Branch right) {
        this.left = left;
        this.right = right;

        this.leaf = false;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public Branch getLeft() {
        return left;
    }

    public Branch getRight() {
        return right;
    }

    public E getInfo() {
        return info;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public void setLeft(Branch left) {
        this.left = left;
    }

    public void setRight(Branch right) {
        this.right = right;
    }

    public void setInfo(E info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Branch{" + "leaf=" + leaf + ", info=" + info + ", left=" + left + ", right=" + right + '}';
    }
}
