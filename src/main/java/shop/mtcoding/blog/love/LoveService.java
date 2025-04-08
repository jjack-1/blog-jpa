package shop.mtcoding.blog.love;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoveService {
    private final LoveRepository loveRepository;
}
