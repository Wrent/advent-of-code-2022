package cz.wrent.advent

fun main() {
	println(partOne(testInput))
	println(partTwo(testInput))

	val result = partOne(input)
	println("3a: $result")
	val resultTwo = partTwo(input)
	println("3b: $resultTwo")
}

private fun partOne(input: String): Int {
	return input.split("\n")
		.map { it.toBackpack() }
		.map { it.findSameItemInBothCompartments() }
		.map { it.toValue() }
		.sum()
}

private fun partTwo(input: String): Int {
	return input.split("\n")
		.chunked(3)
		.map {
			findSameChar(it.get(0).toList(), it.get(1).toList(), it.get(2).toList())
		}.map { it.toValue() }
		.sum()
}

data class Backpack(
	val first: List<Char>,
	val second: List<Char>
) {
	fun findSameItemInBothCompartments(): Char {
		return findSameChar(first, second)
	}
}

private fun findSameChar(vararg inputs: List<Char>): Char {
	return inputs.reduce { acc, s ->
		acc.toSet().intersect(s.toSet()).toList()
	}.first()
}

private fun String.toBackpack(): Backpack {
	return Backpack(this.substring(0, this.length / 2).toList(), this.substring(this.length / 2).toList())
}

private fun Char.toValue(): Int {
	if (this.isUpperCase()) {
		return this.lowercaseChar().code - 70
	} else {
		return this.uppercaseChar().code - 64
	}
}

private const val testInput = """vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw"""

private const val input = """DPstqDdrsqdDtqrFDJDDrmtsJHflSJCLgCphgHHgRHJCRRff
BcBGcQzVBVZcvznTTTvZcGTpCRRRfRCggLflHlhhCZpZCj
vGQnQvnzTzNTTbVnzGBqMqwqDLdPtMmbwqqLLM
wLRFRqvFsFRjfrHddbdbjzdH
lcsnSJPSSVVlGmGrHzbbrGNrdzbz
mSmlnnPlmJmncVDSlSZSlmLBCvtwBvtLCqqswsDBCTWW
pfqPrPgmmhvqdlsdWq
nfjHLJfZcLbVtQWWtndhls
CzJJFLzRzfDwrmggpC
CWfllmlCDFlZZqMfmFBWmWLJVRLVwNNtRVGPpwtGpqbJ
jHndndndcjhscnhHNtRbVtLbGpJbRRcb
HSrvnQzQSMDlLzBCfg
BQRVbgQQBJBbBtVBSSSRWMQbdNvvRPjZjCCdPLNZNNsNCCzd
HwpFpnlGpGZWGvjzPd
FTDmFrrwDpFMtmQVQQcWgc
VhbPshVDPDFWhWsgDNMMbVtmBjwBffpwBntnmnqfnswt
QzzGrTZZdrdlTcCLpRBnmBRRjBCqtptt
rJdGlmLTJdJrvZDbSbDSWDNbFJgD
qrcqTBHTcHgwWWdHRjdWBglBbGPpGvvPbszGzsbPpQfPLwPz
nFVmjhMjFJCSJsQQPQLbLpzCPQ
SnMSVVZSJMNMZNDVFtJtNRBdqBrWrRHWZTllrrjgHq
ZqdqcrPqqrwnQqnrZqjVcqrQwwmNbzNzwbNLzvFbHLbNmBLF
LCDsCsRTfLTDszzNbsbNNHbs
gLfCgShfCgMPlPrVcqrQgn
QSNSLDQDLfqqPwwBNLqgqJMMmmRRCTzHnCHhRzHmfCmh
lGvdbdWdVvsVszpDhHmmlnMpTC
ctbdtVsbbvvsbWZFdVQJqPtgLQBDBwBQPJwJ
dggSSDCPddRWPnSSPWRDgdSrTDsDQDTzQGGTMbsMMVsQfTfV
jmBvtFpBcBhhjljZHphztMsCbsTTCbzsqGqsfz
hccpLmhFlcwCrPLrCPnL
MMHZnGrCfJnfCPggSSGGSSSgLW
qhFhRlDFDlqFsgdvJdWdDdcSvp
wVlhqTbbRNFqswlVVRNbZfHCrntMBrTTZnJMCfnn
sHGZscVGHJMtmRrqzRqqqTqt
SjvvNgjLShWWhhSQNWqmrBzlRllTTgBqnRmq
LNQWLfWhSQvLdCddWWPHMcbHHrJcDGFZCJssFM
mSDjSVQbVGbmqDVbHmqqJTZzPHTHhhRJhwsRPcRJ
tFfFFttFdsNntfpMMppJWwZTzJczJcZPzwWcdJ
vNtgCrpgNptptgCCbbmjbSbvsVjSsjGG
VCQlZJCTPRWsBsjTTT
wvNrnbbvnhdNhLMfGsrGpRFpGpjp
dwndHbHbbLqwwhNcLsqSHSCHJClQtJSttQDPSJ
ZlrvrdvpGBBhlDrshdqJHRPHqPTJzRPPqw
tcftfSgFFgcgLPmPmpnqFwJRHP
pWLCcWNNNNttMNgZvlsvrBrsrjWDjB
wgdCJgDMDwMCwDMCMJsJJfpffVpVfbfrrrrgjgllZp
QFRhvttRthtQzzmpBWbWzWZSVpbSpl
btRttRLttGNqvbFHLwCdDcMwnPPJDnDD
VhmMNllLqGLJQNhRfZHgSPfgSPTqZj
sBwDcwBtsdzvvHZRlPRjDZTgPZ
pWvvBBcBCdzNLVQVWQlNlW
NsSppvSjSPNBNLJJLh
fCGtqQbZZGZQZTbtzbqbCZThddcMBddlJGhdlBMcddgLlJ
zZFTqwLtTRFqTQwvmprnRVSsDrnvVR
FttFTzzvlVHFzTjpbvzbFSDDdVGhdqLGWGJdVDDfsLqG
cmBNCRnwsCcBPMfLLfJGcWhWqfdh
BwPmZZMmZMCsnrwMrmbHHbjbSvSbjvrlHpzj
sZQHCBFHQQQPGQCCHCHwsHFshhtSnnqjbRSSPngnhbRjqVPn
mzLvmDvNNWvNvrzzrMTzJNjqndqbnSnnRgTdtjdbjhTt
WzzWDlJLzLDvMWJJlMzmLJWcpHFQBpBgBHGQGBHfwwBfQQlG
gdpFrdrmrDsqqswdtccgWWCMlChSbhqSlCzBlSqh
TTvjrfjNJPnRQNTQjvNnCSWBVBVbClPSVSbSVhSh
HRvnfTfvjjHZTDsmcHDsrDsdmp
bFChjhbpbjqsntjtns
vdWcfMHfddvrlNMNdWWTNgBqDngBBZBBQZshgSfgnD
JlwrlrlhlcJWcWMwhWNVFpLzwPbbLRFPppVLzm
DtBtgLvgcHzllsTwzSTg
vhhjZrCrZdVdZVSwPMwwTTMGwT
nmpfqrnZJbqBBvRc
nMvSLvWSWPVPvWnSLShFLBjVbpNVGGbVQbbNcBcBBc
sTzJsJszbbQbdQJb
DsDrwTtsCTFhLQSShRwh
RNFQhTQqHNNGRsNqQFNsHhFCwwPLwPqwzfPrrPBwpJSJJw
vMMMblZjddlvWbjbBBfbwCrPPLJppwCL
jDmvcDBlBdjVglgddmvDQRNFtFRGtQhstHNsGFHV
rhLHmZnMrRsZSstZLLtZnhSCNbbmPJVcblTNNTlccpNTjJTj
WFgGddGFFgFDddMblpJjlTJTPc
QGWqBBfWgBqWwFwzMGvzDqSSrHnCHsrssCRZrfRhHLfH
HHzcWqNPmZcqFHPZGBdMRBMDlllWpRDJMl
tTgSvPhbMDJlbJQb
SCTtvtSPftswjvPhTgffVqmGZLmqmCCcqZzZHFNznF
QNpppRrdZvdgzpQZNpgRRgbSwmDDvFGGqwJSsvSGSqGG
HchWBMcBVnnWcHPjHhWcjHTqJFDGMSSqDMwJJbGwSsGGSb
tcCVcBjPjhnWlFrCFNZflQNr
HsVMrqrPqvvgprSrLG
THJWBJDwRFvBgGSzgF
DmhfHnmQncMNVMqPqbcd
SqZmMJqvHJBhHJLp
wsgTVTSsPssjjFVrTrFlhLhCFlBBnHplHLLHfF
zgggdwPrRrsrjjgRwVwdwdQTmvMvZqDZbWqqMSNWNbGQbGZN
fBDBfLZnTLZVVmmDcQMDDV
jPFtJFpHpJqfJFrptwrJdRWRWNpQVmQRMWNVVVNVvQ
zHwJgtFrTlslfghf
wMwTttCCTTSTfBmPzPVZnPZLVVtbnN
ldRRRlRHggGcvcRbZsNzvBVWnnPBWv
hdlJHgpcJccJhQdJrcrhwFMpDqCwCMBqjSqjqpTC
fJfnwJJnnHJgJHTgjsjDccNjcbgNjm
VdLqRRqGVqpRrPpppMBjDNmDctdsBlNjmZdZ
PDQvPQSvpGDrTwfJzzfFnTnS
MnHvnHHMRMzPTlDLPPRGcl
dFnfhFVwhdBPBfGWlPcP
JNrQFsnVtwsgvNzvmMjpzS
BZVPFpNpcNZpmRRPpzcVNhLLnssDjjDGnqjjLDFDjq
mMJbJvtJQQHlJDGCDnjvChDSsv
MQwWJHdQwWrJltQrgfNPmfBcBrpBpZ
ZWZqDsZZqWsWvWLPwPbpHjdtSbSjSCSPPSCp
MFVNMLmFmNzcTTrFrLbjdjbpCdCSbTCShRSd
czNzLrznlGNNrMzMwDlJwJWDwJwqJDvW
GlgchGGVShlQcQfDhzZrNFnFNFNjFzNFcn
dwCtpwHTtPTWpdFNfJJzRzvJNR
tLBBmWHftBttPbLwCHWTsSSQVglqgMsMBDSMGlQS
RDDDGhGfvPPTTPTThn
ZFLMmjpCpfMZzFqmqsCmPjdVBlVBVnWBPNTVbnTV
zHqJMCzLvftRQQHG
nTcbnvPsvdvFzpczVZmMGg
BCCJwSDqhQLJmMMpzGZVFVFB
qhrwJwrJrrzJNqwWLsTTnlTlbnsvbstWsW
vHRbqPJZvRPZhShJvTZllZtgzwlfBGBlsm
VdQjVVCssQVrWrQmTBgBzglmgCBGml
NnpQNpcFpNWshPRLsbSFsH
cVGmVZVwVVMLdvcRttTdbB
ppCQrwzHBtLrttLb
hsFJQzFWCpCqjZGVwlhlPP
HDGRzgWhgfzVWfRpspwRwbwStSwt
ZBPPPmmmTMQMPcZrBmSptSbbQCwtlsNqCwjC
TTLMMmZvPTrMZvFMmcmvrTccDDnfGHJgJhHhnhnLfVhSWDJz
pNrpjzthZPnGrzzWbJLLLbbJZwgSvZCV
MQsFFFDTfMNfRFfBFMdBdwLSvgbSTVCqTgVbbTLvwV
BQlQDMFccQsNmWpPGhpcjr
CTgGRCRglLlLTllL
vMJmhPJcmPBMvhqPDnNNqlWnwDWqsRQs
hcBfcJRPFfvvRvJZBrfMPZdpbSSGtSdtdgtzzSZzbV
NQLzNzzJcrLrSgZSSGgZrR
bTsjqHvcmTHvjgZGDvDpGZRfpg
WqTVPbdnMlLJncQC
hZLBrqLGLMbzLLBhfMMrnnNJlnNnlnJJNNdCJdzN
TWTsWqvtvpTSgRHpVFdjgjCPdgJlCFCFnF
swSTsTwpTVRmVRRRqMDMfqfDwLfbrhLr
NTQHWNQWrQwSTDWlcPPBHZBZbPgZJZ
nmfjCRCfRhndJcjBbcbcbg
nsppRfssfzCnqgzTzrwTwQVTWM
mFjQmDGmbbGjmChrCwdQBHCHWh
qvZZnPvvnngMpnlqpMZnpsTgWHTRCWrVdVDBWRhHBrhHDHhd
vqZgnnqgLvqlPllpnjGDjmNjNLfftftLFD
rfGsjsMNnFMMFddMsttDMgLHGlmJLCPPmHHGmHPlmm
vZcbhQbrRbVZPJLwPTgCLlgb
hchzSBqzQvphnWnrjFdWMqff
WmfPWfVsfqszRDqPqgpvHhvdwddGMmGghM
QtTrtTcSBjtQCctStrTrzhpwjGvGHhngwMHGHvMv
TtTQlQFcSSJlcccBbltQQTTRsPZDsWzRFzWFzPNfsssLPs
QpNNMrjcNMccGNdvLBBlBsBjnsnF
tTSqbbbqCtWWCTWSVTmmCJPwVwnwvFPnsPVnnPfddlvf
HmhJTZWqHqCJJJltqpNGRgzZDcQNrgzDGM
HcLVRhhTRsLRRVjslTscqNQmVNQQgQttqNwNZtmw
nJdBJJhfFPSCbJBJBMbzFbFgmNmtgmvgNgnntNwZQQNNmw
bMbPzJbzCBPrJfdfbBbdCrGHlLTTpWjsGhGTTRTRlc
sJCCpQJQCrfCfnSCrT
vmqgNggzgmZqmPShqBhThfhDhjDhhB
RZNzHRzZSQwHwHVVWc
jtVtvVHgvjJbHjjQPMZdCcwlMdNbdFlNlc
WppSBDzGfBzTBqQWwCFMlwZMwMcZ
zBfnqpRGnSSqTfqpTpSnnHQsjJgQvPJshHtHVh
qJMRMcPPVzVhmsDWfhWT
BglQBNlgZtQBHLHHBnTjWSWmFmwDmWjSsnmF
BdHvgHBvBtZbTpJRPCdcdpGrGJ
pcGcWGWlvQZpzmDbgFmz
HqqnddDdddjzTTggjZgFtT
sHqRwrRsJswLHrMLLRJdqNVVrGffPGWcvSSWlDfGfc
lttTbgRvqvtQRhjLzGjLVh
JJfrHfrdffZJQmZhLLZVVwFj
sBjCfSNNTTqnCnqD
qMtWjSrHftGfjqrJGMqzVzFmBBrzQQwzgBVQVQ
LDChPbThbTcTpCTcnPPQPQzVPvvzQBBWgVBQ
ZLspppLpdZZttdHttqdWMf
htJcJhpMQQWjhNWdJQSCFCTvFBPCTDlMmDCFlM
jjbbsfjwZbLGVVqHCFPvmvDmClTfmP
zjVVRwZwnRJtnNQt
PCPVSzLMMRqGwgMmHmQmDQ
slrrbZZgsfcdsgdhrHFGQHQFwvfwFwDGTv
NclhgpctrrNjllcZdcrpZnPPqzLLSSLqJLtJWCWzCn
PBLSBPVBwpTVppfT
lZCqQQtCQGPJJPtPHHwTwZTTZpwHsfRH
mCtGFDqFGDGQjPGqjJMMlqPgdWgSSgBWWcWzLdgvMzgBMg
cLBrfchhFBcnrgvqvPGvvwSS
QpzpstDDZMwDZqwh
WzpbWTjsbhpQtjThsjJFRNLnfLbfRLRBLlFB
ngnWWqnfgqtfsrWftqsrFWPSdSSdRCTHRSwpRGTfGmSG
VhJhVczJQcvbvvlhBpvlPdmlwHRTGHSRPTSCRGTd
zVcBcMhzcVcvMJJJDpWrsqrtrWLWsgZZFtFD
fbccrJlrffTwJDJTtBtB
hRNNFddsgsFPLLRVVwthMCQTtBwrtT
jrsPGLNjsqPlvGbZbcvScz
HFPmmgQrQzFgrLVPPrLFPNDJNJzzcGbJTbsSzbGGNc
MtvCMhJBdnMhwfhlwnfBfMDCDSjGbqDNGNGqGjDDjsDC
wwnhhdtBBptwdlhlRntRldJFVWRFPmWZFmHRZZmFQPRWmm
WrHNNTBNTTTBwHHcSTrBnSzJPFnpJfpLVfpDVdJLFJLFdD
hRthQvhRQlQmDpfVJFdLlLLj
hMZZbCMvgQgBTBGNGDcWbr
HvQjMRMTzjsCQzHTCFfVVZLPVvfLfPVpZg
GtlbBtSGlSbDdStrhSFCPVDgZgLLgPpPJWPF
rmwSbcbcdbrbGljQjCzCCwnHRqQT
bbgNSHPPgnmMMZtNcMpp
VFzFDFVtCBFDCVFdMlhZMhdhmhmplwZL
JVtBjGRFRttFCGDFGJJDQQgSgTWPPfSSfWbQnHvPWW
NvdBpwNvGNFvpBGGBmLFblrtVTwDttlhtlblfQbQ
SCMMsWCMSRZCqsmWcRWgRRsVtlrtrQbtQftThrQTQtqrtQ
WRscMgZJJCJWzZgSWNLFdBNHGzpFGmBFFG
qghqRVzhLNRLqzLhVztgQdLFdrccCnSpcZdSZcTS
DwvmHDJDsmvDGmHbQBlslMDDCrTCnppTndrdBrFnFTSdCCnZ
GGwJHlGwwvMHJljwwDMVtfhtWWhzqVPPjfQRqz
BsDMPrqPzsDwwCLGmqjpjm
VfFJQlVQcvfwJLJCJppLNp
vfcSHCglCgbgbbbFvSlvQfPsrsZrPzZzDWWStPhtZPDP
gjMsnFgbnllbjMfSZBHHtpHvvvFwhv
DDRZDLdVCLNLJwBCShQHHwwBzv
DNJNTLJRTqWmjWZnjrlmjW
ZTSVSFZCLTnvzfzqvnNL
PfPcJljfMpvtlnztvQtw
PsJMMMWpGcgMHMfjRBThrgrTbBSBFdVSTF
GccBRWjgtQqsTcVQcw
JhJCMJHPLffMChlfLCLHMMrDQsQqDVQsqTDbVvGqDhhzqD
dHGlfnCHlJrNmtdptggpmW
wnDDSBCSBSDLzLLmHLrlwlmpTTqzGJJfpjfjNpfqbpbdpG
MMRhFWWRvZPZRZQhFZMVhVSNqjqpNQffJjbjfbjdTJbp
VMMsWcZRWgMPvRSrSHmsrrtwSHnr
TQchPTgjBcNgPHhhThtNzQdzdsCmRDJnzCCmCdCm
vllVwrfvbSBVFSbVGwlrFGlqRCDzRJCJdzvJRzsdLDDLsdCm
VrMrqSWbfqWbBhhpWjNTttpjjP
rsfvSHHcvwrMPtcQZgnDhGdvJzngLzzJLJ
lWmVlfbCCNFCpBCmTpFFJgzhDLGhmRGhLhmGdgGR
pNBfflVTNpfWTWbWWbjNVqBsscqsqrZSwMwZrMPZSZrZrs
PJPHPJmhhHhlHPQgCndngTbWnqCWDGTD
tSwccFpFqwMcFbGFWvnnWvCW
MwLwLMSwpNBBtctSctfhZHJQhhqmlRlZRNPH
GNzdZhVGvtGZVVgGgtfHHWhpLPPpLWpWWnHf
RjwqRcDTvCrWJWWnlLnnqn
DbrDDwwBwjjsrbDTRTBmwgZmgGgmdttvQvQFSQGFtg
jRgcZRfhmHfZjPZRgHffLFTzzddBTBBFzLDZzBTF
VtsJwSbcStlwMqbtwbvWBWddGGdrFDDWJWrzTT
VwsQQvlbVbVlbNllVwbMmmpnjpfChfQpnhfcCCnH
dFnFjWjTQTFzFWPWPgqhRQRqgVhRqfRqQJ
bStrbpmNGHSrBDmrNBtHBhMVLLqLqVVglrllPVLgPg
tSsbBDmBbbGmmSHDbtmHbtNCjnzscZccjnPcTcdzWcvjswFz
lFCjDhqggMlDvMhFDgqFFzHHwHwwwTpLBwmwqmmpBpwT
GPdPnStGncQGNStZPpBmVZmRmfzTRmVVfL
tWtNdPWzsbtMDCbrCbrjrv
BJHMgLlcMTBLCtbqmMDGppmmMM
ZFPsrrdvwrNvrdNZsvhrrzzRSmJRbJSmbztsmpRSSm
NwhfPZFNdFQPVQdvZFNgjglJLTCQngQWllBcTT
jGlQQvQvpRQRGfnPLfcfGTnP
BMqmdBVBwmFdVMFZdcTPqgLnnggTTLSzPS
FVtMMVcbZVrcZMQCHjHWJJCJDvrW
rPPwVwbpRbbVlllTLCTRqTLL
dNdZssBBCBszHsjhDTQgqLDvlTgDZgll
dSsCNNHMdsdWWWmpGfmPFS
rzCLrsjgZjwcwSZc
wNBNRJpRltHNWWRHBlGlJtRcTZSVBmZDVqZTfBVVTDTVTD
NWPtGJPNGWHvpvtwvWgzQvdvQQzhnsnCvCLM
HHbJhzddMPbPgnDWbZ
BLnjLNvBrrcvvvwnwLrnqrgpPRgRNCWgZDPPpDgRpRWp
jtsBqScStfJQnnVF
QVFSVgQFZZQlQqQSlgQpRppSbRTSTppJJbRpLb
cGwCDwjrnrGvzBzGnwwvDBjnpLbsLTTqRPbsJPMJMWpPns
tcGzrCdtGdQmVZqVNQ
RtTRhncVMTVccShRTctLdfPdJpLPqJhZphHpJs
BzssCmFNWWqWwqwPLH
svzvvsmmFBmsggrGlGMVSMtMRRncSQScRRRl
rmmqrQQwLbbGrrGr
cNJzzzWtWmLCGGbLWWbv
cVtMppchzMBVMcNJcMsRwqZFMlgmggmRgg
mQsQBHFMrbddbRqH
NzhcQNfNNtzvWwZdSrgbrprPrwLbgb
zcJVhTtNNcvcfVZmBmQMGMMljTCmlB
FlldqjSlCgfvPFfvFF
rbnDtVBMbprTsbVVcTDTrpMcmNwgHPgghTmNfLwvfPNLwhdT
drMppdnbbtQDBtbnsBbcrrtbSqSSRCjlQZWllllSRlWRGCCC
nqdCsqbbwdsrHFVJHcwFTc
jPPjtWjPWgRltRLsBRrNpHFDHVFWVVJNNHrD
fgllPGQjBffLjtzSsqvbSSzGvnhS
zsVBzMfHHnzlwwVlqcJJFT
ZzRLvLDzQzTmlWlqWRWF
GbQQvpGvSSpjdQjSQZpQZGLfrgBCsHzrdtCsnfCBHsBgdH
zBLbLWzqqwLMnMZTnHlnsHTvFlFHNT
fjhdcrjjdVdrGSmmdfccGclPvlvPTlGHTFgNvNgqFFvg
pmmcrcRrjSVJchqVccjpRwZMDwCJQBbLDCCbwBWLzL
TDMBgBgLlcjBfMfcVJVmGnnJjvPVCPVv
zzptqHstJqFzzdJJZNvNpvNpnNvGnNZm
dHszrWQhdzHQqdztwQBLSfglfDbfJlJTLg
VTmvrldtGGwmlvmGDHlLnFDCCplFQHLH
ssgjzSzzJCQSSFVVQF
WsRWhgVqRtfvwcddhc
bdlDwznhnNlffMcPTPfzzQ
srCRGRrZCmVTBfBBfTQcZb
brSrrGvRVvWmRsrHrWSbjNJwdDFhnNlwtlnSdnhN
QQqqRfdQQSdjgPmZfBmmPgRhphphJtLmJhTJJhVbTtLhTb
vvlNGzDDDcslcsGDlWHtCFVpcCbThFTtbJFtCh
DrMGlzMVwNGWsWMHDMvlzlMfZdQdQPZfSZRfdrPBfqRZgj
qVHfHNJCHVvvFFbfFlHHnCQQDhLnhhhPZrZnPZPn
mSMszWRMQmhqrnZL
GjtzjSSdRGSjsRtdRMttgGgsqqFNfFcGVvVVvlbHFFGFVFwb"""
