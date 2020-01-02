import { Moment } from 'moment';

export interface ISnowHistoryJobStatus {
  jobId?: number;
  batchId?: number;
  name?: string;
  startTime?: Moment;
  endTime?: Moment;
  sourceCount?: number;
  insertCount?: number;
  deleteCount?: number;
  status?: string;
}

export class SnowHistoryJobStatus implements ISnowHistoryJobStatus {
  constructor(
    public jobId?: number,
    public batchId?: number,
    public name?: string,
    public startTime?: Moment,
    public endTime?: Moment,
    public sourceCount?: number,
    public insertCount?: number,
    public deleteCount?: number,
    public status?: string
  ) {}
}
