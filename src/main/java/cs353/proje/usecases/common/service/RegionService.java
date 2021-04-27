package cs353.proje.usecases.common.service;

import cs353.proje.usecases.common.dto.Region;
import cs353.proje.usecases.common.dto.Response;
import cs353.proje.usecases.common.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {

    @Autowired
    RegionRepository regionRepository;

    public Response getAllRegions(){
        List<Region> allRegions = regionRepository.getAllRegions();
        return new Response(true, "Success", allRegions);
    }

    public Response getRegionName(int regionId){
        String regionName = regionRepository.getRegionName(regionId);
        if(regionName != null)
            return new Response(true, "Success", regionName);
        else
            return new Response(false,"Region not found", regionName);
    }

    public Response addRegion(String regionName){
        int regionId = regionRepository.addRegion(regionName);
        if(regionId > 0)
            return new Response(true, "Region add successful", regionId);
        else
            return new Response(false,"Region add unsuccessful", null);
    }

    public Response deleteRegion(int regionId){
        boolean deleted = regionRepository.deleteRegion(regionId);
        if(deleted)
            return new Response(true, "Region delete successful",null);
        else
            return new Response(false, "Region delete unsuccessful",null);
    }


}
