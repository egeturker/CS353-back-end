package cs353.proje.usecases.common.repository;

import cs353.proje.usecases.common.dto.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RegionRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    RowMapper<Region> regionRowMapper = (rs, rowNum) -> {
        Region region = new Region();
        region.setRegion_id(rs.getInt("region_id"));
        region.setRegion_name(rs.getString("region_name"));

        return region;
    };

    RowMapper<Integer> integerRowMapper = (rs, rowNum) -> rs.getInt(1);

    public List<Region> getAllRegions(){
        String sql = "SELECT * FROM region";
        return jdbcTemplate.query(sql,regionRowMapper);
    }

    public String getRegionName(int regionId){
        String sql = "SELECT region_name FROM region WHERE region_id = ?";
        Object[] params = {regionId};

        return jdbcTemplate.queryForObject(sql, params, String.class);
    }

    public int addRegion(String regionName){
        String sql = "INSERT INTO region(region_name) VALUES(?)";
        Object[] params = {regionName};

        if(jdbcTemplate.update(sql, params) < 0)
            return -1;
        else{
            String sqlGetId = "SELECT LAST_INSERT_ID()";
            return jdbcTemplate.queryForObject(sqlGetId, integerRowMapper);
        }
    }

    public boolean deleteRegion(int regionId){
        String sql = "DELETE FROM region WHERE region_id = ?";
        Object[] params = {regionId};

        return jdbcTemplate.update(sql, params) == 1;
    }





}
