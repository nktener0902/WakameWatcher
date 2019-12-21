package com.wakame.observer.raspberry.domain.sampling;

import com.wakame.observer.raspberry.domain.sampling.camera.Photograph;
import lombok.Value;

@Value
public class SampledData {

    private Photograph photograph;

}
