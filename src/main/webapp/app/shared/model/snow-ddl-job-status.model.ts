import { Moment } from 'moment';

export interface ISnowDDLJobStatus {
  jobId?: number;
  batchId?: number;
  name?: string;
  startTime?: Moment;
  endTime?: Moment;
  status?: string;
}

export class SnowDDLJobStatus implements ISnowDDLJobStatus {
  constructor(
    public jobId?: number,
    public batchId?: number,
    public name?: string,
    public startTime?: Moment,
    public endTime?: Moment,
    public status?: string
  ) {}
}
