package vn.ptit.restaurant.common.catalog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import vn.ptit.restaurant.dto.response.PageResponse;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CatalogServiceHelper {

    public static Pageable toPageable(BaseSearchRequest req) {
        Sort sort = req.getSortDir().equalsIgnoreCase("asc") ?
                Sort.by(req.getSortBy()).ascending() : Sort.by(req.getSortBy()).descending();
        return PageRequest.of(req.getPage(), req.getSize(), sort);
    }

    public static <E, R> PageResponse<R> toPageResponse(Page<E> page, Function<E, R> mapper) {
        List<R> content = page.getContent().stream().map(mapper).collect(Collectors.toList());
        return PageResponse.<R>builder()
                .content(content)
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    public static <T> Specification<T> buildLikeSpecification(String q, String... fields) {
        return (root, query, cb) -> {
            List<Predicate> preds = new ArrayList<>();
            if (q != null && !q.trim().isEmpty()) {
                String like = "%" + q.trim().toLowerCase() + "%";
                List<Predicate> orPreds = new ArrayList<>();
                for (String f : fields) {
                    orPreds.add(cb.like(cb.lower(root.get(f).as(String.class)), like));
                }
                preds.add(cb.or(orPreds.toArray(new Predicate[0])));
            }
            return cb.and(preds.toArray(new Predicate[0]));
        };
    }
}
