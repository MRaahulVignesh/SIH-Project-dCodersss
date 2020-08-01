import React from 'react';
import { useTheme } from '@material-ui/core/styles';
import { LineChart, Line, XAxis, YAxis, Label, ResponsiveContainer } from 'recharts';
import Title from './Title';

// Generate Sales Data
function createData(date, amount) {
  return { date, amount };
}

const data = [
  createData('7/25', 0),
  createData('7/26', 300),
  createData('7/27', 600),
  createData('7/28', 800),
  createData('7/29', 1500),
  createData('7/30', 2000),
  createData('7/31', 2400),
  createData('8/1', 2400),
  createData('8/2', undefined),
];

export default function Chart() {
  const theme = useTheme();

  return (
    <React.Fragment>
      <Title>Today</Title>
      <ResponsiveContainer>
        <LineChart
          data={data}
          margin={{
            top: 16,
            right: 16,
            bottom: 16,
            left: 24,
          }}
        >
          <XAxis dataKey="date" stroke={theme.palette.text.secondary}>
            <Label
                position="bottom"
                style={{ textAnchor: 'middle', fill: theme.palette.text.primary }}
              >
              Date
            </Label>
          </XAxis>
          <YAxis stroke={theme.palette.text.secondary}>
            <Label
              angle={270}
              position="left"
              style={{ textAnchor: 'middle', fill: theme.palette.text.primary }}
            >
              Sales (₹)
            </Label>
          </YAxis>
          <Line type="monotone" dataKey="amount" stroke={theme.palette.primary.main} dot={false} />
        </LineChart>
      </ResponsiveContainer>
    </React.Fragment>
  );
}