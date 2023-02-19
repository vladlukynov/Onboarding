package com.api.courseservice.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.courseservice.DTO.BlockInCourseDTO;
import com.api.courseservice.DTO.CourseDTO;
import com.api.courseservice.DTO.CoursePage;
import com.api.courseservice.DTO.ItemInBlock;
import com.api.courseservice.model.BlockInCourse;
import com.api.courseservice.model.Course;
import com.api.courseservice.model.Feedback;
import com.api.courseservice.model.Test;
import com.api.courseservice.model.TestResults;
import com.api.courseservice.model.TextMaterials;
import com.api.courseservice.repository.BlockInCourseRepository;
import com.api.courseservice.repository.CourseRepository;
import com.api.courseservice.repository.FeedbackRepository;
import com.api.courseservice.repository.FeedbackResultsRepository;
import com.api.courseservice.repository.TestRepository;
import com.api.courseservice.repository.TestResultsRepository;
import com.api.courseservice.repository.TextMaterialsResultsRepository;

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

    @Autowired
    BlockInCourseRepository blockInCourseRepository;

    @Autowired
    TextMaterialsResultsRepository textMaterialsResultsRepository;

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
        int countPassedFeedback
                = feedbackResultsRepository.countFeedbackResultsByUserIdAndCourseId(userId, courseId);
        int countPassedTest
                = testResultsRepository.countTestResultsByUserIdAndCourseId(userId, courseId);
        int countPassedTextMaterials
                = textMaterialsResultsRepository.countTextMaterialsByUserIdAndCourseId(userId, courseId);
        return (countPassedTest + countPassedFeedback + countPassedTextMaterials) != 0;
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
        return ((double) (value2 + value)) / countTheme * 100;
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
    public List<CourseDTO> getStartedOrPassedCoursesForUser(Long userId, Long postId) {
        List<CourseDTO> courseList = courseRepository.getCoursesByPostId(postId);
        List<CourseDTO> result = new ArrayList<>();
        for (CourseDTO courseDTO : courseList) {
            if (checkCourseStartOrPassed(courseDTO.getId(), userId)) {
                courseDTO.setCountThemes(getCountTheme(courseDTO.getId()));
                courseDTO.setPercentageOfCompletion(getPercentageOfCompletion(courseDTO.getId(), userId));
                result.add(courseDTO);
            } else {
                break;
            }
        }
        if (result.isEmpty()) {
            courseList.get(0).setCountThemes(getCountTheme(courseList.get(0).getId()));
            courseList.get(0).setPercentageOfCompletion(getPercentageOfCompletion(courseList.get(0).getId(), userId));
            result.add(courseList.get(0));
        }
        return result;
    }

    @Override
    public CoursePage getCourseForCoursePage(Long courseId, Long userId) {
        List<BlockInCourse> list = blockInCourseRepository.getBlockInCoursesByCourse_Id(courseId);
        List<BlockInCourseDTO> blockInCourseDTOList = new ArrayList<>();
        boolean flag = false;
        for (BlockInCourse blockInCourse : list) {
            BlockInCourseDTO blockInCourseDTO = new BlockInCourseDTO();
            blockInCourseDTO.setId(blockInCourse.getId());
            blockInCourseDTO.setName(blockInCourse.getName());

            List<Feedback> feedbackCompletedList
                    = feedbackResultsRepository.findFeedbacksByUserIdAndBlockId(userId, blockInCourse.getId());
            List<Feedback> feedbackAllList = blockInCourse.getFeedbacks();
            feedbackAllList.removeAll(feedbackCompletedList);
            feedbackAllList.sort(Comparator.comparing(x -> ((Feedback) x).getNumberInBlock()));
            Integer min = feedbackAllList.isEmpty() ? null : feedbackAllList.get(0).getNumberInBlock();

            List<Test> testCompletedList = testResultsRepository.findByUserIdAndBlockId(userId, blockInCourse.getId());
            List<Test> testAllList = blockInCourse.getTests();
            testAllList.removeAll(testCompletedList);
            testAllList.sort(Comparator.comparing(x -> ((Test) x).getNumberInBlock()));
            if (min == null) {
                min = testAllList.isEmpty() ? null : testAllList.get(0).getNumberInBlock();
            } else {
                min = Math.min(testAllList.isEmpty() ? min : testAllList.get(0).getNumberInBlock(), min);
            }

            List<TextMaterials> textMaterialsCompletedList
                    = textMaterialsResultsRepository.findTextMaterialsByUserIdAndBlockId(userId, blockInCourse.getId());
            List<TextMaterials> textMaterialsAllList = blockInCourse.getTextMaterials();
            textMaterialsAllList.removeAll(textMaterialsCompletedList);
            textMaterialsAllList.sort(Comparator.comparing(x -> ((TextMaterials) x).getNumberInBlock()));
            if (min == null) {
                min = textMaterialsAllList.isEmpty() ? null : textMaterialsAllList.get(0).getNumberInBlock();
            } else {
                min = Math.min(textMaterialsAllList.isEmpty() ? min : textMaterialsAllList.get(0).getNumberInBlock(), min);
            }

            List<ItemInBlock> items = new ArrayList<>();

            for (Feedback feedback : feedbackCompletedList) {
                items.add(new ItemInBlock(feedback.getId(), feedback.getTitle(), 0, 0, feedback.getNumberInBlock()));
            }
            for (Feedback feedback : feedbackAllList) {
                if (!flag && feedback.getNumberInBlock().equals(min)) {
                    flag = true;
                    items.add(new ItemInBlock(feedback.getId(), feedback.getTitle(), 0, 1, feedback.getNumberInBlock()));
                } else {
                    items.add(new ItemInBlock(feedback.getId(), feedback.getTitle(), 0, 2, feedback.getNumberInBlock()));
                }
            }

            for (Test test : testCompletedList) {
                items.add(new ItemInBlock(test.getId(), test.getTitle(), 1, 0, test.getNumberInBlock()));
            }
            for (Test test : testAllList) {
                if (!flag && test.getNumberInBlock().equals(min)) {
                    flag = true;
                    items.add(new ItemInBlock(test.getId(), test.getTitle(), 1, 1, test.getNumberInBlock()));
                } else {
                    items.add(new ItemInBlock(test.getId(), test.getTitle(), 1, 2, test.getNumberInBlock()));
                }
            }

            for (TextMaterials textMaterial : textMaterialsCompletedList) {
                flag = true;
                items.add(new ItemInBlock(textMaterial.getId(), textMaterial.getTitle(), 2, 0, textMaterial.getNumberInBlock()));
            }
            for (TextMaterials textMaterials : textMaterialsAllList) {
                if (!flag && textMaterials.getNumberInBlock().equals(min)) {
                    items.add(new ItemInBlock(textMaterials.getId(), textMaterials.getTitle(), 2, 1, textMaterials.getNumberInBlock()));
                } else {
                    items.add(new ItemInBlock(textMaterials.getId(), textMaterials.getTitle(), 2, 2, textMaterials.getNumberInBlock()));
                }
            }
            blockInCourseDTO.setItemsInBlock(items);
            blockInCourseDTO.setNumberInCourse(blockInCourse.getNumberInCourse());
            blockInCourseDTOList.add(blockInCourseDTO);
        }
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            CoursePage coursePage = new CoursePage();
            coursePage.setId(optionalCourse.get().getId());
            coursePage.setName(optionalCourse.get().getName());
            coursePage.setDescription(optionalCourse.get().getDescription());
            blockInCourseDTOList.sort(Comparator.comparing(BlockInCourseDTO::getNumberInCourse));
            coursePage.setBlocks(blockInCourseDTOList);
            coursePage.setCountThemes(blockInCourseDTOList.size());
            return coursePage;
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public boolean checkAccessUserCourse(Long userId, Long postId, Long courseId) {
        List<CourseDTO> list = getStartedOrPassedCoursesForUser(userId, postId);
        for (CourseDTO courseDTO : list) {
            if (courseDTO.getId().equals(courseId)) {
                return true;
            }
        }
        return false;
    }

//    @Override
//    public Feedback findNextFeedbackInCourseById(Long id, Long userId) {
//        Optional<Course> course = courseRepository.findById(id);
//        if (course.isEmpty()) {
//            throw new EntityNotFoundException();
//        }
//        Course crs = course.get();
//        if (crs.getFeedbacks().isEmpty()) {
//            throw new EntityNotFoundException();
//        }
//        Set<FeedbackResults> feedbackResultsSet = feedbackResultsRepository.findByUserIdEquals(userId);
//        Set<Integer> feedbackIdResults = new HashSet<>();
//        for (FeedbackResults res : feedbackResultsSet) {
//            feedbackIdResults.add(res.getFeedback().getNumberInCourse());
//        }
//        Set<Feedback> feedbackSet = crs.getFeedbacks();
//        Set<Integer> feedbackId = new HashSet<>();
//        for (Feedback res : feedbackSet) {
//            feedbackId.add(res.getNumberInCourse());
//        }
//        feedbackId.removeAll(feedbackIdResults);
//        List<Integer> testList = feedbackId.stream().sorted().collect(Collectors.toList());
//        Optional<Feedback> fbck = feedbackSet.stream().filter(x -> Objects.equals(x.getNumberInCourse(), testList.get(0))).findFirst();
//        return fbck.get();
//    }
}
