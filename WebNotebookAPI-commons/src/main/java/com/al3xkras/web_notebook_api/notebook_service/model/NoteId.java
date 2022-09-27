package com.al3xkras.web_notebook_api.notebook_service.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class NoteId implements Serializable {
    private long userId;
    private long noteId;
}
