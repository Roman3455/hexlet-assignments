package exercise.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;

import exercise.model.BaseEntity;
import jakarta.persistence.EntityManager;

// BEGIN
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public abstract class ReferenceMapper {

    @Autowired
    protected EntityManager entityManager;

    public <T extends BaseEntity> T toEntity(Long id, @TargetType Class<T> clazz) {
        return id != null ? entityManager.find(clazz, id) : null;
    }
}
// END
