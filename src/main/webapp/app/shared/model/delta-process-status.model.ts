import { Moment } from 'moment';

export interface IDeltaProcessStatus {
  jobId?: number;
  processId?: number;
  name?: string;
  runBy?: string;
  jobStartTime?: Moment;
  jobEndTime?: Moment;
  tableCount?: number;
  successCount?: number;
  failureCount?: number;
  jobStatus?: string;
}

export class DeltaProcessStatus implements IDeltaProcessStatus {
  constructor(
    public jobId?: number,
    public processId?: number,
    public name?: string,
    public runBy?: string,
    public startTime?: Moment,
    public endTime?: Moment,
    public totalCount?: number,
    public successCount?: number,
    public failureCount?: number,
    public jobStatus?: string
  ) {}
}
