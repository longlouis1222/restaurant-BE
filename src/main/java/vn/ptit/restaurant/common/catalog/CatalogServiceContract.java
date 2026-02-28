package vn.ptit.restaurant.common.catalog;

import vn.ptit.restaurant.dto.response.PageResponse;

import java.util.List;

public interface CatalogServiceContract<E, ID, CreateReq, UpdateReq, Resp, SearchReq extends BaseSearchRequest> {

    Resp create(CreateReq request);

    Resp update(ID id, UpdateReq request);

    void delete(ID id);

    Resp getById(ID id);

    List<Resp> getAll();

    PageResponse<Resp> search(SearchReq request);
}
