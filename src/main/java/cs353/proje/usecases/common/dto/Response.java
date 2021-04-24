package cs353.proje.usecases.common.dto;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Response {
    private boolean success;
    private String message;
    private Object data;
}
