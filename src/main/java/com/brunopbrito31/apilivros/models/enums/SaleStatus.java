package com.brunopbrito31.apilivros.models.enums;

public enum SaleStatus {
    STARTED("started"),
    INPROGRESS("inprogress"),
    FINISH("finish"),
    CANCELED("canceled");

    private String selectedStatus;

    private SaleStatus(String status) {
        this.selectedStatus = status;
    }
}
