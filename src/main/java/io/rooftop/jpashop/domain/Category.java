package io.rooftop.jpashop.domain;

import io.rooftop.jpashop.domain.Item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
public class Category {

    @Id @GeneratedValue
    @Column(name = "CATEGORY_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Category parent;

    private String name;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<Category>();

    @ManyToMany
    @JoinTable(name = "CATEGORY_ITEM",
            joinColumns = @JoinColumn(name = "CATEGORY_ID"),
            inverseJoinColumns = @JoinColumn(name = "ITEM_ID"))
    private List<Item> itemList = new ArrayList<Item>();

    // 연관관계 매핑
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
