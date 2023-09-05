package com.sesacthon.foreco.category.repository;

import static com.sesacthon.foreco.category.entity.QTrash.trash;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sesacthon.foreco.category.entity.Trash;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Repository
public class TrashQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;

  public List<Trash> findTrashesWithSearchCond(String keyword, Long categoryId) {
    return jpaQueryFactory
        .selectFrom(trash)
        .where(containKeyword(keyword), equalCategoryId(categoryId))
        .fetch();
  }

  private BooleanExpression containKeyword(String keyword) {
    return StringUtils.hasText(keyword) ? trash.name.contains(keyword) : Expressions.asBoolean(true); // 비어 있지 않으면 true 반환
  }

  private BooleanExpression equalCategoryId(Long categoryId) {
    return categoryId != null ? trash.parentTrash.id.eq(categoryId) : Expressions.asBoolean(true); // categoryId가 null이 아니면 비교, 아니면 true 반환
  }

}
