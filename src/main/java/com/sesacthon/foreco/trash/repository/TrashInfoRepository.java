package com.sesacthon.foreco.trash.repository;

import com.sesacthon.foreco.trash.entity.TrashInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrashInfoRepository extends JpaRepository<TrashInfo, Long> {

  Optional<TrashInfo> findByTrashIdAndRegionId(Long trashId, Long regionId);
}
