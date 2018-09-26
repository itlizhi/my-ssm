package com.enreach.ssm.infrastructure;


import java.util.List;

public class PagedList<E> {

    /**
     * 每页的的数量
     */
    private int pageSize;

    /**
     * 当前页码
     */
    private int pageCurrent;

    /**
     * 总条数
     */
    private long totalItem;

    /**
     * 数据集合
     */
    private List<E> items;

    public PagedList() {
    }

    public PagedList(int pageSize, int pageCurrent, long totalItem, List<E> items) {
        this.pageSize = pageSize;
        this.pageCurrent = pageCurrent;
        this.totalItem = totalItem;
        this.items = items;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCurrent() {
        return pageCurrent;
    }

    public void setPageCurrent(int pageCurrent) {
        this.pageCurrent = pageCurrent;
    }

    public long getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(long totalItem) {
        this.totalItem = totalItem;
    }

    public List<E> getItems() {
        return items;
    }

    public void setItems(List<E> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "PagedList{" +
                "pageSize=" + pageSize +
                ", pageCurrent=" + pageCurrent +
                ", totalItem=" + totalItem +
                ", items=" + items +
                '}';
    }
}
