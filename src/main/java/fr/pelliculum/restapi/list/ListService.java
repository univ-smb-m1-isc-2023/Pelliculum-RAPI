package fr.pelliculum.restapi.list;

import fr.pelliculum.restapi.configuration.exceptions.UserNotFoundException;
import fr.pelliculum.restapi.configuration.handlers.Response;
import fr.pelliculum.restapi.entities.List;
import fr.pelliculum.restapi.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListService {

    private final ListRepository listRepository;

    /**
     * Get a list by id or throw an exception (404)
     * @param id {@link Long} list id
     * @return {@link List} list
     */
    public List findListByIdOrNull(Long id) {
        return listRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("List not found with id: " + id));
    }

    /**
     * Get all lists
     * @return {@link List} lists
     */
    public ResponseEntity<?> getLists() {
        return Response.ok("Lists successfully founded !", listRepository.findAll());
    }

    /**
     * Get a list by id
     * @param id {@link Long} list id
     * @return {@link List} list
     */
    public ResponseEntity<?> getList(Long id) {
        return Response.ok("List successfully founded !", listRepository.findById(id).orElse(null));
    }

    /**
     * Create a list
     * @param list {@link List} list
     * @return {@link List} list
     */
    public ResponseEntity<?> createList(List list) {
        return Response.ok("List successfully created !", listRepository.save(list));
    }

    /**
     * Add a movie to a list
     * @param id {@link Long} list id
     * @param movieId {@link Long} movie id
     * @return {@link List} list
     */
    public ResponseEntity<?> addMovieToList(Long id, Long movieId) {
        List list = findListByIdOrNull(id);
        list.getMovies().add(movieId);
        return Response.ok("Movie successfully added to list !", listRepository.save(list));
    }

    /**
     * Remove a movie from a list
     * @param id {@link Long} list id
     * @param movieId {@link Long} Movie id
     * @return
     */
    public ResponseEntity<?> removeMovieFromList(Long id, Long movieId) {
        List list = findListByIdOrNull(id);
        list.getMovies().remove(movieId);
        return Response.ok("Movie successfully removed from list !", listRepository.save(list));
    }

    public ResponseEntity<?> deleteList(Long id) {
        listRepository.deleteById(id);
        return Response.ok("List successfully deleted !");
    }

    public ResponseEntity<?> updateList(Long id, List list) {
        List listToUpdate = findListByIdOrNull(id);
        listToUpdate.setName(list.getName());
        listToUpdate.setDescription(list.getDescription());
        listToUpdate.setIsPublic(list.getIsPublic());
        return Response.ok("List successfully updated !", listRepository.save(listToUpdate));
    }


}
