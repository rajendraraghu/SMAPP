import { Moment } from 'moment';

export interface ISnowParseJobStatus {
  jobId?: number;
  batchId?: number;
  name?: string;
  startTime?: Moment;
  endTime?: Moment;
  status?: string;
}

export class SnowParseJobStatus implements ISnowParseJobStatus {
  constructor(
    public jobId?: number,
    public batchId?: number,
    public name?: string,
    public startTime?: Moment,
    public endTime?: Moment,
    public status?: string
  ) {}
}
