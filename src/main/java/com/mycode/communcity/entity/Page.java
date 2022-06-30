package com.mycode.communcity.entity;

/**
 * 用于封装分页相关的信息
 */
public class Page {
    //当前页码
    private int current = 1;
    // 显示上限
    private int limit = 10;
    // 数据的总数,用于计算总的页数
    private int rows;
    // 查询路径(复用分页链接)
    private String path;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if (current >= 1){
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if(limit >= 1 && limit <=100){
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if(rows>=0){
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    /**
     * 查询用:根据当前页的页码算出起始行
     */
    public int getOffset(){
        //(current -1)*limit
        return (current -1) * limit;
    }
    /**
     * 获得总页数
     */
    public int getTotal(){
        //总行数/每页数据 [+1]
        if (rows%limit==0){
            return rows/limit;
        }else {
            return rows/limit + 1;
        }
    }
    /**
     * 获取起始页码
     */
    public int getFrom(){
        int from =  current-2;
        return from<1 ? 1:from;
    }
    /**
     * 获取最后一页
     */
    public int getTo(){
        int total = getTotal();
        int to = current +2;
        return to > total? total : to;
    }
}
