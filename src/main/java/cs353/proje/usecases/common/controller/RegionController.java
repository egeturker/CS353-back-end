package cs353.proje.usecases.common.controller;

import cs353.proje.usecases.common.dto.Response;
import org.springframework.web.bind.annotation.*;

@RestController("RegionController")
@RequestMapping("/region")
@CrossOrigin
public class RegionController {
    
    @GetMapping("/allRegions")
    public Response getAllRegions()
    {
        return null;
    }

    @GetMapping("/name/id={id}")
    public Response getRegionName(@PathVariable(value="id") int region_id)
    {
        return null;
    }

    @PostMapping("/new")
    public Response addRegion(@RequestBody String region_name)
    {
        return null;
    }

    @PostMapping("/delete")
    public Response deleteRegion(@RequestBody int region_id)
    {
        return null;
    }
}
