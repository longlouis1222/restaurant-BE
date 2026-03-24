package vn.ptit.restaurant.common.catalog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ptit.restaurant.dto.response.PageResponse;
import vn.ptit.restaurant.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Generic abstract service to handle common CRUD + search for catalog-like entities.
 * Subclasses must provide repository access and mapping methods.
 */
public abstract class AbstractCatalogService<E, ID, CreateReq, UpdateReq, Resp, SearchReq extends BaseSearchRequest>
        implements CatalogServiceContract<E, ID, CreateReq, UpdateReq, Resp, SearchReq>
{

    protected abstract JpaRepository<E, ID> getRepository();

    protected abstract JpaSpecificationExecutor<E> getSpecRepository();

    protected abstract ID generateIdIfNeeded(CreateReq request);

    protected abstract E createEntity(CreateReq request, ID id);

    protected abstract void applyUpdate(E entity, UpdateReq request);

    protected abstract Resp toResponse(E entity);

    protected abstract String[] getSearchFields();

    @Override
    public Resp create(CreateReq request) {
        ID id = generateIdIfNeeded(request);
        E entity = createEntity(request, id);
        getRepository().save(entity);
        return toResponse(entity);
    }

    @Override
    public Resp update(ID id, UpdateReq request) {
        E entity = getRepository().findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy entity: " + id));
        applyUpdate(entity, request);
        getRepository().save(entity);
        return toResponse(entity);
    }

    @Override
    public void delete(ID id) {
        E entity = getRepository().findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy entity: " + id));
        getRepository().delete(entity);
    }

    @Override
    public void deleteMany(List<ID> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        List<E> entities = getRepository().findAllById(ids);
        if (!entities.isEmpty()) {
            getRepository().deleteAll(entities);
        }
    }

    @Override
    public Resp getById(ID id) {
        E entity = getRepository().findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy entity: " + id));
        return toResponse(entity);
    }

    @Override
    public List<Resp> getAll() {
        return getRepository().findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PageResponse<Resp> search(SearchReq request) {
        Specification<E> spec = CatalogServiceHelper.buildLikeSpecification(request.getQ(), getSearchFields());
        Pageable pageable = CatalogServiceHelper.toPageable(request);
        Page<E> page = getSpecRepository().findAll(spec, pageable);
        return CatalogServiceHelper.toPageResponse(page, this::toResponse);
    }
}
