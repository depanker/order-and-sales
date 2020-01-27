package com.depanker.orderandsales.entities.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;


@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Version
    private Integer version;

    @CreatedDate
    @Column(nullable = false, insertable = true, updatable = false, columnDefinition = "datetime default current_timestamp")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date createdOn;

    @LastModifiedDate
    @Column(nullable = false, insertable = true, updatable = true, columnDefinition = "datetime default current_timestamp")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date modifiedOn;

    @PrePersist
    protected void onCreate() {
        createdOn = modifiedOn = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedOn = new Date();
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (this.id == null || obj == null || !(this.getClass().equals(obj.getClass()))) {
            return false;
        }

        BaseEntity that = (BaseEntity) obj;

        return this.id.equals(that.getId());
    }
}
