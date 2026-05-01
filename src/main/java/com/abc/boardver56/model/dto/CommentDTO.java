package com.abc.boardver56.model.dto;

import java.time.LocalDateTime;

public class CommentDTO {
    private Integer commentId;
    private Integer postId;
    private String authorId;
    private String password;
    private String content;
    private String ipHash;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    public CommentDTO(){}

    public CommentDTO(Integer commentId, Integer postId, String authorId, String password, String content, String ipHash, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.commentId = commentId;
        this.postId = postId;
        this.authorId = authorId;
        this.password = password;
        this.content = content;
        this.ipHash = ipHash;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIpHash() {
        return ipHash;
    }

    public void setIpHash(String ipHash) {
        this.ipHash = ipHash;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "BV5DTOComment{" +
                "commentId=" + commentId +
                ", postId=" + postId +
                ", authorId='" + authorId + '\'' +
                ", password='" + password + '\'' +
                ", content='" + content + '\'' +
                ", ipHash='" + ipHash + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
