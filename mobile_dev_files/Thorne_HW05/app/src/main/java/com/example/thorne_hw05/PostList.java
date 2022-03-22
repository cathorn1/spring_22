package com.example.thorne_hw05;

import java.util.ArrayList;

public class PostList {
    String status, page;
    ArrayList<Post> posts;
    int pageSize, totalCount;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "PostList{" +
                "status='" + status + '\'' +
                ", page='" + page + '\'' +
                ", posts=" + posts +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                '}';
    }
}
