package com.great.deploy.dolpin.repository;


import static org.assertj.core.api.Assertions.assertThat;

import com.great.deploy.dolpin.model.Pins;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
public class PinsRepositoryTest {

  public PinsRepositoryTest() {
  }

  @Autowired
  PinsRepository pinsRepository;

  @Test
  public void pinsTest(){
    Set<Long> longs = new HashSet<>();
    longs.add(1L);longs.add(2L);longs.add(3L);
    List<Long> a = new ArrayList<>();
    a.add(1L);a.add(2L);a.add(3L);
    List<Pins> byCelebrityGroup_idIn = pinsRepository.findPinsByCelebrityGroupIdIn(longs);
    List<Long> collect = byCelebrityGroup_idIn.stream().map(b -> b.getId())
        .collect(Collectors.toList());
    assertThat(collect).isEqualTo(a);
    System.out.println(byCelebrityGroup_idIn);

    pinsRepository.flush();
  }

}