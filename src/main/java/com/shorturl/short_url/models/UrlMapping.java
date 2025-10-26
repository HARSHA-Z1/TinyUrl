package com.shorturl.short_url.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schema_seq")
    @SequenceGenerator(name = "schema_seq", sequenceName = "schema_id_seq", initialValue = 100000, allocationSize = 1)
    private Long id;

    private Long clickCount = 0L;

    @Column(length = 2000)
    private String longUrl;
    private String shortUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "urlMapping")
    private List<RedirectEvent> redirectEventList;

}
