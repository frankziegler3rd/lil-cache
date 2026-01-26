/**
 * RESTful controller for handling cache requests.
 *
 * @author Frank Ziegler
 * @version 1.0.0
 */

package com.fz3rd.LilCache.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("cache")
public class CacheController {

    private final CacheService cs;

    public CacheController(CacheService cs) {
        this.cs = cs;
    }

    /**
     * get and getOrDefault logic built into one endpoint. Default values are URL query parameters. 
     */
    @GetMapping("/{key}")
    public ResponseEntity<?> getOrDefault(@PathVariable String key,
                                          @RequestParam(name = "default", required = false) String defaultValue) {
        String value = cs.get(key);
        if(value != null) {
            return ResponseEntity.ok(value);
        }
        if(defaultValue != null) {
            return ResponseEntity.ok(defaultValue);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{key}")
    public ResponseEntity<?> put(@PathVariable String key,
                                 @RequestBody String value) {
        cs.put(key, value);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<?> delete(@PathVariable String key) {
        cs.delete(key);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<?> clear() {
        cs.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/size")
    public ResponseEntity<?> size() {
        return ResponseEntity<?>.ok(cs.size());
    }

}
