package fr.pelliculum.restapi.list;

import fr.pelliculum.restapi.entities.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/list/")
public class ListController {

    private final ListService listService;

    @GetMapping
    public ResponseEntity<?> getLists() {
        return listService.getLists();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getList(@PathVariable Long id) {
        return listService.getList(id);
    }

    @PostMapping
    public ResponseEntity<?> createList(@RequestBody List list) {
        return listService.createList(list);
    }

    @PostMapping("{id}/movies/{movieId}")
    public ResponseEntity<?> addMovieToList(@PathVariable Long id, @PathVariable Long movieId) {
        return listService.addMovieToList(id, movieId);
    }

    @DeleteMapping("{id}/movies/{movieId}")
    public ResponseEntity<?> removeMovieFromList(@PathVariable Long id, @PathVariable Long movieId) {
        return listService.removeMovieFromList(id, movieId);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteList(@PathVariable Long id) {
        return listService.deleteList(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateList(@PathVariable Long id, @RequestBody List list) {
        return listService.updateList(id, list);
    }


}
