package eu.toon.dao;

import eu.toon.domain.TemperatureAdviceDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TemperatureAdviceDetailsDAO extends JpaRepository<TemperatureAdviceDetails, Integer> {
    @Query("select count(*) from TemperatureAdviceDetails tad where tad.minTemperature = :minTemperature and tad.maxTemperature = :maxTemperature")
    public int findByTemperatureRange(@Param("minTemperature") double minTemperature, @Param("maxTemperature") double maxTemperature);

    @Query(value = "select tad.advice from TemperatureAdviceDetails tad where :temperature between tad.minTemperature and tad.maxTemperature")
    public String getAdvice(@Param("temperature") double temperature);
}
