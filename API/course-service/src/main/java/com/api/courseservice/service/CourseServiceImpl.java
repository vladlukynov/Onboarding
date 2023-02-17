package com.api.courseservice.service;

import java.util.*;
import java.util.stream.Collectors;

import com.api.courseservice.model.*;
import com.api.courseservice.repository.FeedbackResultsRepository;
import com.api.courseservice.repository.TestRepository;
import com.api.courseservice.repository.TestResultsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.courseservice.DTO.CourseDTO;
import com.api.courseservice.repository.CourseRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    TestRepository testRepository;

    @Autowired
    TestResultsRepository testResultsRepository;

    @Autowired
    FeedbackResultsRepository feedbackResultsRepository;

    @Override
    public List<CourseDTO> getCoursesForPost(Long postId) {
        return courseRepository.getCoursesByPostId(postId);
    }

    @Override
    public void deleteById(Long id){
        courseRepository.deleteById(id);
    }

    @Override
    public Test findNextTestInCourseById(Long id, Long userId){
        Optional<Course> course = courseRepository.findById(id);
        if(course.isEmpty()){
            throw new EntityNotFoundException();
        }
        Course crs = course.get();
        if(crs.getTests().isEmpty()){
            throw new EntityNotFoundException();
        }
        Set<TestResults> testResultsSet = testResultsRepository.findByUserIdEquals(userId);
        Set<Integer> testIdResults = new HashSet<>();
        for(TestResults res:testResultsSet){
            testIdResults.add(res.getTest().getNumberInCourse());
        }
        Set<Test> testSet = crs.getTests();
        Set<Integer> testId = new HashSet<>();
        for(Test res:testSet){
            testId.add(res.getNumberInCourse());
        }
        testId.removeAll(testIdResults);
        List<Integer> testList = testId.stream().sorted().collect(Collectors.toList());
        Optional<Test> tst = testSet.stream().filter(x -> Objects.equals(x.getNumberInCourse(), testList.get(0))).findFirst();
        return tst.get();
    }

    @Override
    public boolean isNextTest(Long id, Long userId){
        Optional<Course> course = courseRepository.findById(id);
        if(course.isEmpty()){
            throw new EntityNotFoundException();
        }
        Course crs = course.get();
        if(crs.getTests().isEmpty()){
            return false;
        }else {
            if(crs.getFeedbacks().isEmpty()){
                return true;
            }
        }
        Set<TestResults> testResultsSet = testResultsRepository.findByUserIdEquals(userId);
        Set<Integer> testIdResults = new HashSet<>();
        for(TestResults res:testResultsSet){
            testIdResults.add(res.getTest().getNumberInCourse());
        }
        Set<FeedbackResults> FeedbackResultsSet = feedbackResultsRepository.findByUserIdEquals(userId);
        Set<Integer> feedbackIdResults = new HashSet<>();
        for(FeedbackResults res:FeedbackResultsSet){
            feedbackIdResults.add(res.getFeedback().getNumberInCourse());
        }
        Set<Test> testSet = crs.getTests();
        Set<Integer> testId = new HashSet<>();
        for(Test res:testSet){
            testId.add(res.getNumberInCourse());
        }
        Set<Feedback> feedbackSet = crs.getFeedbacks();
        Set<Integer> feedbackId = new HashSet<>();
        for(Feedback res:feedbackSet){
            feedbackId.add(res.getNumberInCourse());
        }
        testId.removeAll(testIdResults);
        feedbackId.removeAll(feedbackIdResults);
        List<Integer> testList = testId.stream().sorted().collect(Collectors.toList());
        List<Integer> feedbackList = feedbackId.stream().sorted().collect(Collectors.toList());
        return testList.get(0) < feedbackList.get(0);
    }

    @Override
    public Feedback findNextFeedbackInCourseById(Long id, Long userId){
        Optional<Course> course = courseRepository.findById(id);
        if(course.isEmpty()){
            throw new EntityNotFoundException();
        }
        Course crs = course.get();
        if(crs.getFeedbacks().isEmpty()){
            throw new EntityNotFoundException();
        }
        Set<FeedbackResults> feedbackResultsSet = feedbackResultsRepository.findByUserIdEquals(userId);
        Set<Integer> feedbackIdResults = new HashSet<>();
        for(FeedbackResults res:feedbackResultsSet){
            feedbackIdResults.add(res.getFeedback().getNumberInCourse());
        }
        Set<Feedback> feedbackSet = crs.getFeedbacks();
        Set<Integer> feedbackId = new HashSet<>();
        for(Feedback res:feedbackSet){
            feedbackId.add(res.getNumberInCourse());
        }
        feedbackId.removeAll(feedbackIdResults);
        List<Integer> testList = feedbackId.stream().sorted().collect(Collectors.toList());
        Optional<Feedback> fbck = feedbackSet.stream().filter(x -> Objects.equals(x.getNumberInCourse(), testList.get(0))).findFirst();
        return fbck.get();
    }
}
