package com.al3xkras.web_notebook_api.notebook_service.entity;

import com.al3xkras.web_notebook_api.notebook_service.model.NoteId;
import com.al3xkras.web_notebook_api.user_service.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@IdClass(NoteId.class)
@Table(name = "note")
public class Note {
    @Id
    @Column(name = "user_id")
    private long userId;
    @Id
    @Column(name = "note_id")
    private long noteId;
    @Column(name = "note_text", nullable = false, columnDefinition = "nvarchar(2500)")
    private String noteText;

    //TODO replace cascade type all
    @JoinColumn(name = "user_id",insertable = false,updatable = false)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;
}
