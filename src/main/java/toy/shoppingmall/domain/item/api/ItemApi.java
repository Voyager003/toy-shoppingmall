package toy.shoppingmall.domain.item.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemApi {

    @GetMapping("/endpoint")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public String someEndpoint() {
        return "some-endpoint";
    }
}
