package com.example.graduate.mapper;

import java.util.List;

/**
 * BaseMapper interface for converting between entity and DTO objects.
 *
 * @param <E> the type of the entity
 * @param <D> the type of the DTO
 */
public interface BaseMapper<E, D> {
    E toEntity(D dto);

    D toDTO(E entity);

    List<E> toEntity(List<D> dtoList);

    List<D> toDTO(List<E> entityList);
}
