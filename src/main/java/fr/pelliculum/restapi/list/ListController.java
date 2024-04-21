package fr.pelliculum.restapi.list;

import fr.pelliculum.restapi.entities.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lists/")
@RequiredArgsConstructor
public class ListController {

    private final ListService listService;

    @GetMapping("{id}")
    public ResponseEntity<?> getList(@PathVariable Long id) {
        return listService.getListById(id);
    }

    @GetMapping
    public ResponseEntity<?> getLists(@RequestParam(name = "isPublic", required = false, defaultValue = "false") Boolean isPublic) {
        return listService.getLists(isPublic);
    }

    @GetMapping("user/{username}")
    public ResponseEntity<?> getListsByUsername(@PathVariable String username, @RequestParam(name = "isPublic", required = false, defaultValue = "false") Boolean isPublic) {
        return listService.getListsByUsername(username, isPublic);
    }

    @PostMapping("")
    public ResponseEntity<?> createList(@RequestBody ListCreateBody listCreateBody) {
        return listService.createList(listCreateBody.getName(), listCreateBody.getDescription(), listCreateBody.getIsPublic(), listCreateBody.getUsername());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteList(@PathVariable Long id) {
        return listService.deleteList(id);
    }

    @PostMapping("{id}/movies/{movieId}")
    public ResponseEntity<?> addMovieToList(@PathVariable Long id, @PathVariable Long movieId) {
        return listService.addMovieToList(id, movieId);
    }

    @DeleteMapping("{id}/movies/{movieId}")
    public ResponseEntity<?> removeMovieFromList(@PathVariable Long id, @PathVariable Long movieId) {
        return listService.removeMovieFromList(id, movieId);
    }




}
