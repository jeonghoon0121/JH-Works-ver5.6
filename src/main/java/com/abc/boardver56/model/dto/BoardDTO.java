package com.abc.boardver56.model.dto;

public class BoardDTO {
    private Integer boardId;
    private String name;
    private String description;
    private Boolean userActive;
    private Boolean adminActive;
    private Integer sortOrder;
    public BoardDTO(){}

    public BoardDTO(Integer boardId, String name, String description, Boolean userActive, Boolean adminActive, Integer sortOrder) {
        this.boardId = boardId;
        this.name = name;
        this.description = description;
        this.userActive = userActive;
        this.adminActive = adminActive;
        this.sortOrder = sortOrder;
    }

    public Integer getBoardId() {
        return boardId;
    }

    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getUserActive() {
        return userActive;
    }

    public void setUserActive(Boolean userActive) {
        this.userActive = userActive;
    }

    public Boolean getAdminActive() {
        return adminActive;
    }

    public void setAdminActive(Boolean adminActive) {
        this.adminActive = adminActive;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public String toString() {
        return "BoardDTO{" +
                "boardId=" + boardId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", userActive=" + userActive +
                ", adminActive=" + adminActive +
                ", sortOrder=" + sortOrder +
                '}';
    }
}
