package com.yuantek.mi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileInfo {
    private String path;

    public FileInfo(String path) {
        this.path = path;
    }
}
