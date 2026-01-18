package com.gcnbl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应数据传输对象
 *
 * @author wuguocheng
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDTO {

    private String code;

    private String msg;

    private Object data;

    public ResponseDTO(String code, String message) {
        this.code = code;
        this.msg = message;
    }
}
