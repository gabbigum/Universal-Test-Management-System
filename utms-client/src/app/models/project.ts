export interface Project {
    id: string;    
    name: string;
    description: string;
    testRuns: TestRun[];
}

export interface TestRun {
    runId: number;
    status: string;
    suites: Suite[];
}

export interface Suite {
    name: string;
    status: string;
    tests: Test[];
}

export interface Test {
    name: string;
    description: string;
    output: string;
    error: string;
    status: string;
    startTime: Date;
    endTime: Date;
    enabled: boolean;
}
