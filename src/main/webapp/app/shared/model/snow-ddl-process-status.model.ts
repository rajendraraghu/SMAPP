import { Moment } from 'moment';

export interface ISnowDDLProcessStatus {
  batchId?: number;
  processId?: number;
  name?: string;
  runBy?: string;
  // sourceSystem?: string;
  startTime?: Moment;
  endTime?: Moment;
  totalObjects?: number;
  successObjects?: number;
  errorObjects?: number;
  status?: string;
}

export class SnowDDLProcessStatus implements ISnowDDLProcessStatus {
  constructor(
    public batchId?: number,
    public processId?: number,
    public name?: string,
    public runBy?: string,
    // public sourceSystem?: string,
    public startTime?: Moment,
    public endTime?: Moment,
    public totalObjects?: number,
    public successObjects?: number,
    public errorObjects?: number,
    public status?: string
  ) {}
}
