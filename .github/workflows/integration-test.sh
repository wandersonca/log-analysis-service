#!/bin/bash
set -e

# Start WildFly server
docker compose up -d
echo "Waiting for WildFly to start..."
sleep 60

# Add required application and metrics
echo "Adding application and metrics..."
curl -X POST 'http://localhost/management-console/application' --data-raw 'name=TestApplication&description=This+is+a+test...' --silent >/dev/null
curl -X POST 'http://localhost/management-console/metric' --data-raw 'name=TestALL&application=1&logLevel=ALL&regex=' --silent >/dev/null
curl -X POST 'http://localhost/management-console/metric' --data-raw 'name=TestINFORegex&application=1&logLevel=INFO&regex=Received' --silent >/dev/null
curl -X POST 'http://localhost/management-console/metric' --data-raw 'name=TestDEBUG&application=1&logLevel=DEBUG&regex=' --silent >/dev/null

# Wait for metrics to load next heartbeat (every 60s)
echo "Waiting for metrics to load..."
sleep 60

# POST log messages
echo "POSTing log messages..."
cat << EOF |
081109 203519 143 INFO dfs.DataNode$DataXceiver: Receiving block blk_-1608999687919862906 src: /10.250.10.6:40524 dest: /10.250.10.6:50010
081109 203519 145 DEBUG dfs.DataNode$PacketResponder: PacketResponder 1 for block blk_-1608999687919862906 terminating
081109 203519 145 INFO dfs.DataNode$PacketResponder: Received block blk_-1608999687919862906 of size 91178 from /10.250.19.102
EOF
curl --silent --data-binary "@-"  http://127.0.0.1:8080/log-collector/api/application/1/upload/file

# Wait for stats to get uploaded to database (every 15s)
echo "Waiting for stats to get uploaded to database..."
sleep 15

# check for results
echo "Checking for results..."
exit_code=0
results=$(curl --silent http://localhost/management-console/dashboard)
if [[ $(echo $results | grep -c "<td>TestALL</td><td>ALL</td><td></td><td>Tue Aug 09 20:00:00 UTC 2011 - Tue Aug 09 21:00:00 UTC 2011</td><td>3</td>") != "1" ]]; then
  echo "TestALL failed"
  exit_code=1
fi
if [[ $(echo $results | grep -c "<td>TestINFORegex</td><td>INFO</td><td>Received</td><td>Tue Aug 09 20:00:00 UTC 2011 - Tue Aug 09 21:00:00 UTC 2011</td><td>1</td>") != "1" ]]; then
  echo "TestINFORegex failed"
  exit_code=1
fi
if [[ $(echo $results | grep -c "<td>TestDEBUG</td><td>DEBUG</td><td></td><td>Tue Aug 09 20:00:00 UTC 2011 - Tue Aug 09 21:00:00 UTC 2011</td><td>1</td>") != 1 ]]; then
  echo "TestDEBUG failed"
  exit_code=1
fi

if [[ "$exit_code" == "1" ]]; then
    echo "Test failed - see output:"
    echo "$results"
fi


# Stop WildFly server
echo "Stopping WildFly..."
docker compose down

echo "Script finished. Exiting with $exit_code"
exit $exit_code
