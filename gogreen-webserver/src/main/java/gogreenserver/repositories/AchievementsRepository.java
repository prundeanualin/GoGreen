package gogreenserver.repositories;

import gogreenserver.entity.Achievements;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementsRepository extends JpaRepository<Achievements, Long> {
}
