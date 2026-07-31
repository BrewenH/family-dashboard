package com.brewen.familydashboard.common.exception;

import java.util.List;

public record ValidationErrorResponse(List<String> errors) {
}
