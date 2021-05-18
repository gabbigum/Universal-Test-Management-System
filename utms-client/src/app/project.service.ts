import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Project, TestRun } from './models/project';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  projects: BehaviorSubject<Project[]> = new BehaviorSubject([] as Project[]);

  private readonly URL = "http://127.0.0.1:8080/projects";

  constructor(private http: HttpClient) {
    this.getProjects();
  }

  getProjects() {
    this.http.get<Project[]>(this.URL).subscribe((projects) => {
      this.projects.next(projects);
    });
  }
    
  computePassRate(testRun: TestRun): number {
    let passedTests = 0;
    testRun.suites.forEach((suite) => {
      suite.tests.forEach((test) => {
        if (test.enabled) {
          if (test.status === "passed") {
            passedTests++;
          }
        }
      });
    })
    return passedTests;
  }

  getNumberOfTests(testRun: TestRun): number {
    let numOfTests = 0;
    testRun.suites.forEach((suite) => {
      suite.tests.forEach((test) => {
        if (test.enabled) {
          numOfTests++;
        }
      });
    })
    return numOfTests;
  }
}
