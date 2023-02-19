package com.api.courseservice.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "text_materials")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TextMaterials {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "block_id")
    private BlockInCourse block;

    private Integer numberInBlock;

    @OneToMany(mappedBy = "textMaterials", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<TextMaterialsResults> textMaterialsResults;
}