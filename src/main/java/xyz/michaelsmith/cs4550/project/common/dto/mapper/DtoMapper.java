package xyz.michaelsmith.cs4550.project.common.dto.mapper;

public interface DtoMapper<E, D> {
    D map(E entity);

    default E mapReverse(D dto) {
        throw new UnsupportedOperationException();
    }
}
