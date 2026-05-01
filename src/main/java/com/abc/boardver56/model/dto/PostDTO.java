package com.abc.boardver56.model.dto;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public class PostDTO {
    private Integer postId;
    private Integer boardId;
    private String authorId;
    private String password;
    private String ipHash;
    private String attachmentUrl;
    private String title;
    private String content;
    private String thumbnailUrl;
    private String accessLevel;
    private String status;
    private Integer viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean notice;
    private Boolean secret;
    private MultipartFile file;
    private Boolean old;
    private String category;

    public PostDTO() {
    }

    public PostDTO(Integer postId, Integer boardId, String authorId, String password, String ipHash, String attachmentUrl, String title, String content, String thumbnailUrl, String accessLevel, String status, Integer viewCount, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean notice, Boolean secret, MultipartFile file, Boolean old, String category) {
        this.postId = postId;
        this.boardId = boardId;
        this.authorId = authorId;
        this.password = password;
        this.ipHash = ipHash;
        this.attachmentUrl = attachmentUrl;
        this.title = title;
        this.content = content;
        this.thumbnailUrl = thumbnailUrl;
        this.accessLevel = accessLevel;
        this.status = status;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.notice = notice;
        this.secret = secret;
        this.file = file;
        this.old = old;
        this.category = category;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getBoardId() {
        return boardId;
    }

    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
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

    public String getIpHash() {
        return ipHash;
    }

    public void setIpHash(String ipHash) {
        this.ipHash = ipHash;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
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

    public Boolean getNotice() {
        return notice;
    }

    public void setNotice(Boolean notice) {
        this.notice = notice;
    }

    public Boolean getSecret() {
        return secret;
    }

    public void setSecret(Boolean secret) {
        this.secret = secret;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Boolean getOld() {
        return old;
    }

    public void setOld(Boolean old) {
        this.old = old;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "PostDTO{" +
                "postId=" + postId +
                ", boardId=" + boardId +
                ", authorId='" + authorId + '\'' +
                ", password='" + password + '\'' +
                ", ipHash='" + ipHash + '\'' +
                ", attachmentUrl='" + attachmentUrl + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", accessLevel='" + accessLevel + '\'' +
                ", status='" + status + '\'' +
                ", viewCount=" + viewCount +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", notice=" + notice +
                ", secret=" + secret +
                ", file=" + file +
                ", old=" + old +
                ", category='" + category + '\'' +
                '}';
    }
}