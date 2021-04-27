package cs353.proje.usecases.common.controller;

import cs353.proje.usecases.common.dto.Response;
import cs353.proje.usecases.common.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//Ege

@RestController("RegionController")
@RequestMapping("/region")
@CrossOrigin
public class RegionController {

    @Autowired
    RegionService regionService;
    
    @GetMapping("/allRegions")
    public Response getAllRegions()
    {
        return regionService.getAllRegions();
    }

    @GetMapping("/name/id={id}")
    public Response getRegionName(@PathVariable(value="id") int region_id)
    {
        return regionService.getRegionName(region_id);
    }

    @PostMapping("/new")
    public Response addRegion(@RequestBody String region_name)
    {
        return regionService.addRegion(region_name);
    }

    @PostMapping("/delete")
    public Response deleteRegion(@RequestBody int region_id)
    {
        return regionService.deleteRegion(region_id);
    }
}
