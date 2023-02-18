package com.api.courseservice.service;

import java.util.*;
import java.util.stream.Collectors;

import com.api.courseservice.model.*;
import com.api.courseservice.repository.FeedbackRepository;
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

    @Autowired
    FeedbackRepository feedbackRepository;

    @Override
    public List<CourseDTO> getCoursesForUser(Long userId, Long postId) {
        List<CourseDTO> courseList = courseRepository.getCoursesByPostId(postId);
        boolean flag = false;
        Long prevCourseId = -1L;
        for (CourseDTO courseDTO : courseList) {
            if (!checkCourseStartOrPassed(courseDTO.getId(), userId) && !flag) {
                if (prevCourseId == -1L) {
                    Set<TestResults> testResults = testResultsRepository.findByUserIdAndCourseId(userId, courseDTO.getId());
                    List<Test> tests = testRepository.getByCourse(courseDTO.getId());
                    int feedbackResultCount = feedbackResultsRepository.countFeedbackResultsByUserIdAndCourseId(userId, courseDTO.getId());
                    int feedbackCount = feedbackRepository.getCountQuestionsByCourse(courseDTO.getId());
                    flag = testResults.size() != tests.size() || feedbackResultCount != feedbackCount;
                    courseDTO.setClosed(checkCourseStartOrPassed(courseDTO.getId(), userId));
                } else {
                    Set<TestResults> testResults = testResultsRepository.findByUserIdAndCourseId(userId, prevCourseId);
                    List<Test> tests = testRepository.getByCourse(prevCourseId);
                    int feedbackResultCount = feedbackResultsRepository.countFeedbackResultsByUserIdAndCourseId(userId, prevCourseId);
                    int feedbackCount = feedbackRepository.getCountQuestionsByCourse(prevCourseId);
                    if (testResults.size() == tests.size() && feedbackResultCount == feedbackCount) {
                        courseDTO.setClosed(checkCourseStartOrPassed(courseDTO.getId(), userId));
                    }
                    testResults = testResultsRepository.findByUserIdAndCourseId(userId, courseDTO.getId());
                    tests = testRepository.getByCourse(courseDTO.getId());
                    feedbackResultCount = feedbackResultsRepository.countFeedbackResultsByUserIdAndCourseId(userId, courseDTO.getId());
                    feedbackCount = feedbackRepository.getCountQuestionsByCourse(courseDTO.getId());
                    flag = testResults.size() != tests.size() || feedbackResultCount != feedbackCount;
                }
            } else {
                courseDTO.setClosed(!checkCourseStartOrPassed(courseDTO.getId(), userId));
            }
            courseDTO.setCountThemes(getCountTheme(courseDTO.getId()));
            courseDTO.setPercentageOfCompletion(getPercentageOfCompletion(courseDTO.getId(), userId));
            prevCourseId = courseDTO.getId();
        }
        return courseList;
    }

    private boolean checkCourseStartOrPassed(Long courseId, Long userId) {
        Set<TestResults> testResults = testResultsRepository.findByUserIdAndCourseId(userId, courseId);
        return testResults.size() != 0;
    }

    @Override
    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Test findNextTestInCourseById(Long id, Long userId) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isEmpty()) {
            throw new EntityNotFoundException();
        }
        Course crs = course.get();
        if (crs.getTests().isEmpty()) {
            throw new EntityNotFoundException();
        }
        Set<TestResults> testResultsSet = testResultsRepository.findByUserIdEquals(userId);
        Set<Integer> testIdResults = new HashSet<>();
        for (TestResults res : testResultsSet) {
            testIdResults.add(res.getTest().getNumberInCourse());
        }
        Set<Test> testSet = crs.getTests();
        Set<Integer> testId = new HashSet<>();
        for (Test res : testSet) {
            testId.add(res.getNumberInCourse());
        }
        testId.removeAll(testIdResults);
        List<Integer> testList = testId.stream().sorted().collect(Collectors.toList());
        Optional<Test> tst = testSet.stream().filter(x -> Objects.equals(x.getNumberInCourse(), testList.get(0))).findFirst();
        return tst.get();
    }

    @Override
    public boolean isNextTest(Long id, Long userId) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isEmpty()) {
            throw new EntityNotFoundException();
        }
        Course crs = course.get();
        if (crs.getTests().isEmpty()) {
            return false;
        } else {
            if (crs.getFeedbacks().isEmpty()) {
                return true;
            }
        }
        Set<TestResults> testResultsSet = testResultsRepository.findByUserIdEquals(userId);
        Set<Integer> testIdResults = new HashSet<>();
        for (TestResults res : testResultsSet) {
            testIdResults.add(res.getTest().getNumberInCourse());
        }
        Set<FeedbackResults> FeedbackResultsSet = feedbackResultsRepository.findByUserIdEquals(userId);
        Set<Integer> feedbackIdResults = new HashSet<>();
        for (FeedbackResults res : FeedbackResultsSet) {
            feedbackIdResults.add(res.getFeedback().getNumberInCourse());
        }
        Set<Test> testSet = crs.getTests();
        Set<Integer> testId = new HashSet<>();
        for (Test res : testSet) {
            testId.add(res.getNumberInCourse());
        }
        Set<Feedback> feedbackSet = crs.getFeedbacks();
        Set<Integer> feedbackId = new HashSet<>();
        for (Feedback res : feedbackSet) {
            feedbackId.add(res.getNumberInCourse());
        }
        testId.removeAll(testIdResults);
        feedbackId.removeAll(feedbackIdResults);
        List<Integer> testList = testId.stream().sorted().collect(Collectors.toList());
        List<Integer> feedbackList = feedbackId.stream().sorted().collect(Collectors.toList());
        return testList.get(0) < feedbackList.get(0);
    }

    @Override
    public double getPercentageOfCompletion(Long courseId, Long userId) {
        Integer value = feedbackResultsRepository.countFeedbackByCourseIdAndUserID(userId, courseId);
        value = value == null ? 0 : value;
        Integer value2 = testResultsRepository.countTestResultsByUserIdAndCourseId(userId, courseId);
        int countTheme = getCountTheme(courseId);
        if (value.equals(value2 + value)) {
            return 100;
        }
        return ((double) (value2 + value)) / countTheme;
    }

    @Override
    public int getCountTheme(Long courseId) {
        return testRepository.getCountTestByCourse(courseId) + feedbackRepository.getCountFeedbackByCourse(courseId);
    }

    @Override
    public Course getCourseById(Long id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            return courseOptional.get();
        }
        throw new EntityNotFoundException();
    }

    @Override
    public Feedback findNextFeedbackInCourseById(Long id, Long userId) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isEmpty()) {
            throw new EntityNotFoundException();
        }
        Course crs = course.get();
        if (crs.getFeedbacks().isEmpty()) {
            throw new EntityNotFoundException();
        }
        Set<FeedbackResults> feedbackResultsSet = feedbackResultsRepository.findByUserIdEquals(userId);
        Set<Integer> feedbackIdResults = new HashSet<>();
        for (FeedbackResults res : feedbackResultsSet) {
            feedbackIdResults.add(res.getFeedback().getNumberInCourse());
        }
        Set<Feedback> feedbackSet = crs.getFeedbacks();
        Set<Integer> feedbackId = new HashSet<>();
        for (Feedback res : feedbackSet) {
            feedbackId.add(res.getNumberInCourse());
        }
        feedbackId.removeAll(feedbackIdResults);
        List<Integer> testList = feedbackId.stream().sorted().collect(Collectors.toList());
        Optional<Feedback> fbck = feedbackSet.stream().filter(x -> Objects.equals(x.getNumberInCourse(), testList.get(0))).findFirst();
        return fbck.get();
    }
}
