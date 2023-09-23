package jp.co.sony.ppog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import jp.co.sony.ppog.entity.Language;
import jp.co.sony.ppog.utils.LanguageId;

/**
 * 言語リポジトリ
 *
 * @author shubonnsei
 * @since 1.00
 */
@Repository
public interface LanguageRepository extends JpaRepository<Language, LanguageId>, JpaSpecificationExecutor<Language> {
}
